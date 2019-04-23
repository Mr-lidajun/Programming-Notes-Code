package net.atom.library.clink.core;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;
import net.atom.library.clink.box.StringReceivePacket;
import net.atom.library.clink.box.StringSendPacket;
import net.atom.library.clink.core.ReceiveDispatcher.ReceivePacketCallback;
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
    private SendDispatcher sendDispatcher;

    public void setup(SocketChannel socketChannel) throws IOException {
        this.channel = socketChannel;

        IoContext context = IoContext.get();
        SocketChannelAdapter adapter = new SocketChannelAdapter(socketChannel,
                context.getIoProvider(), this);

        this.sender = adapter;
        this.receiver = adapter;
    }

    public void send(String msg) {
        StringSendPacket packet = new StringSendPacket(msg);
        sendDispatcher.send(packet);
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void onChannelClosed(SocketChannel channel) {

    }


    private ReceivePacketCallback receivePacketCallback = packet -> {
        if (packet instanceof StringReceivePacket) {
            String msg = ((StringReceivePacket) packet).string();
            onReceiveNewMessage(msg);
        }
    };


    protected void onReceiveNewMessage(String str) {
        System.out.println(key.toString() + ":" + str);
    }
}
