package net.atom.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 接收者
 */
public interface Receiver extends Closeable {

    void setReceiveListener(IoArgs.IoArgsEventListener listener);
    /**
     * 异步接收
     * @param args
     * @return
     * @throws IOException
     */
    boolean receiverAsync(IoArgs args) throws IOException;
}
