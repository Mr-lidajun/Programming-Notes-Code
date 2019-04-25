package net.atom.library.clink.impl.async;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.atom.library.clink.box.StringReceivePacket;
import net.atom.library.clink.core.IoArgs;
import net.atom.library.clink.core.IoArgs.IoArgsEventListener;
import net.atom.library.clink.core.ReceiveDispatcher;
import net.atom.library.clink.core.ReceivePacket;
import net.atom.library.clink.core.Receiver;
import net.atom.library.clink.utils.CloseUtils;

public class AsyncReceiveDispatcher implements ReceiveDispatcher {
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    private Receiver receiver;
    private final ReceivePacketCallback callback;

    private IoArgs ioArgs = new IoArgs();
    private ReceivePacket packetTemp;
    private byte[] buffer;
    private int total;
    private int position;

    public AsyncReceiveDispatcher(Receiver receiver,
            ReceivePacketCallback callback) {
        this.receiver = receiver;
        this.receiver.setReceiveListener(ioArgsEventListener);
        this.callback = callback;
    }


    @Override
    public void start() {
        registerReceive();
    }

    @Override
    public void stop() {

    }

    @Override
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            ReceivePacket packet = this.packetTemp;
            if (packet != null) {
                packet = null;
                CloseUtils.close(packet);
            }
        }
    }

    private void CloseAndNotify() {
        CloseUtils.close(this);
    }

    private void registerReceive() {
        try {
            receiver.receiverAsync(ioArgs);
        } catch (IOException e) {
            CloseAndNotify();
        }
    }

    /**
     * 解析数据到Packet
     * @param args
     */
    private void assemblePacket(IoArgs args) {
        if (packetTemp == null) {
            int length = args.readLength();
            packetTemp = new StringReceivePacket(length);
            buffer = new byte[length];
            total = length;
            position = 0;
        }

        int count = args.writeTo(buffer, 0);
        if (count > 0) {
            packetTemp.save(buffer, count);
            position += count;
        }

        // 检查是否已完成一份Packet
        if (position == total) {
            completePacket();
            packetTemp = null;
        }
    }

    /**
     * 完成数据接收操作
     */
    private void completePacket() {
        ReceivePacket packet = this.packetTemp;
        CloseUtils.close(packet);
        callback.onReceivePacketCompleted(packet);
    }

    private IoArgs.IoArgsEventListener ioArgsEventListener = new IoArgsEventListener() {
        @Override
        public void onStarted(IoArgs args) {
            int receiveSize;
            if (packetTemp == null) {
                receiveSize = 4;
            } else {
                receiveSize = Math.min(total - position, args.capacity());
            }

            // 设置本次接收数据大小
            args.limit(receiveSize);
        }

        @Override
        public void onCompleted(IoArgs args) {
            assemblePacket(args);
            // 继续接收下一条数据
            registerReceive();
        }
    };
}
