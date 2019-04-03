package net.atom.library.clink.core;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;
import net.atom.library.clink.core.IoArgs.IoArgsEventListener;
import net.atom.library.clink.impl.SocketChannelAdapter;
import net.atom.library.clink.impl.SocketChannelAdapter.OnChannelStatusChangedListener;

/**
 * 连接抽象
 * */
public class Connector implements Closeable, OnChannelStatusChangedListener {
    private UUID key = UUID.randomUUID();
    private SocketChannel channel;
    private Sender sender;
    private Receiver receiver;

    public void setup(SocketChannel socketChannel) throws IOException {
        this.channel = socketChannel;

        IoContext context = IoContext.get();
        SocketChannelAdapter adapter = new SocketChannelAdapter(socketChannel,
                context.getIoProvider(), this);

        this.sender = adapter;
        this.receiver = adapter;

        // 读取下一条消息
        readNextMessage();
    }

    private void readNextMessage() {
        if (receiver != null) {
            try {
                receiver.receiverAsync(echoReceiveListener);
            } catch (IOException e) {
                System.out.println("开始接收数据异常：" + e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void onChannelClosed(SocketChannel channel) {

    }

    IoArgsEventListener echoReceiveListener = new IoArgs.IoArgsEventListener() {

        @Override
        public void onStarted(IoArgs args) {

        }

        @Override
        public void onCompleted(IoArgs args) {
            // 打印
            onReceiveNewMessage(args.bufferString());
            // 读取下一条数据
            readNextMessage();
        }
    };

    protected void onReceiveNewMessage(String str) {
        System.out.println(key.toString() + ":" + str);
    }
}
