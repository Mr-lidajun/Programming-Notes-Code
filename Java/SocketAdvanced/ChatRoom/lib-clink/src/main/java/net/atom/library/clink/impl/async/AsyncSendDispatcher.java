package net.atom.library.clink.impl.async;

import java.io.Closeable;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import net.atom.library.clink.core.IoArgs;
import net.atom.library.clink.core.IoArgs.IoArgsEventListener;
import net.atom.library.clink.core.SendDispatcher;
import net.atom.library.clink.core.SendPacket;
import net.atom.library.clink.core.Sender;
import net.atom.library.clink.utils.CloseUtils;

public class AsyncSendDispatcher implements SendDispatcher {
    private final Sender sender;
    private final Queue<SendPacket> queue = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean isSending = new AtomicBoolean(false);
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    private IoArgs ioArgs = new IoArgs();
    private SendPacket packetTemp;

    // 当前发送的packet的大小，以及进度
    private int total;
    private int position;

    public AsyncSendDispatcher(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void send(SendPacket packet) {
        queue.offer(packet);
        if (isSending.compareAndSet(false, true)) {
            sendNextPacket();
        }
    }

    @Override
    public void cancel(SendPacket packet) {

    }

    private SendPacket takePacket() {
        SendPacket packet = queue.poll();
        if (packet != null && packet.isCanceled()) {
            // 已取消，不用发送
            return takePacket();
        }
        return packet;
    }

    private void sendNextPacket() {
        SendPacket temp = this.packetTemp;
        if (temp != null) {
            CloseUtils.close(temp);
        }
        SendPacket packet = this.packetTemp = takePacket();
        if (packet == null) {
            // 队列为空，取消状态发送
            isSending.set(false);
            return;
        }

        total = packet.length();
        position = 0;

        sendCurrentPacket();
    }

    private void sendCurrentPacket() {
        IoArgs args = ioArgs;

        // 开始，清理
        args.startWriting();

        if (position >= total) {
            sendNextPacket();
            return;
        } else if (position == 0) {
            // 首包，需要携带长度信息
            args.writeLength(total);
        }

        byte[] bytes = packetTemp.bytes();
        // 把bytes的数据写入到IoArgs
        int count = args.readFrom(bytes, position);
        position += count;

        // 完成封装
        args.finishWriting();

        try {
            sender.sendAsync(args, ioArgsEventListener);
        } catch (IOException e) {
            CloseAndNotify();
        }

    }

    private void CloseAndNotify() {
        CloseUtils.close(this);
    }

    @Override
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            isSending.set(false);
            SendPacket packet = this.packetTemp;
            if (packet != null) {
                packet = null;
                CloseUtils.close(packet);
            }
        }
    }

    IoArgsEventListener ioArgsEventListener = new IoArgs.IoArgsEventListener() {

        @Override
        public void onStarted(IoArgs args) {

        }

        @Override
        public void onCompleted(IoArgs args) {
            // 继续发送当前包
            sendCurrentPacket();
        }
    };
}
