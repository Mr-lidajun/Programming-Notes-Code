package net.atom.library.clink.impl;

import net.atom.library.clink.core.IoArgs;
import net.atom.library.clink.core.Receiver;
import net.atom.library.clink.core.Sender;

import java.io.Closeable;
import java.io.IOException;

/**
 * 发送与接收具体实现
 */
public class SocketChannelAdapter implements Sender, Receiver, Closeable {
    @Override
    public boolean receiverAsync(IoArgs.IoArgsEventListener listener) throws IOException {
        return false;
    }

    @Override
    public boolean sendAsync(IoArgs args, IoArgs.IoArgsEventListener listener) throws IOException {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
