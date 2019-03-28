package net.atom.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 接收者
 */
public interface Receiver extends Closeable {
    /**
     * 异步接收
     * @param listener 接收状态回调
     * @return
     * @throws IOException
     */
    boolean receiverAsync(IoArgs.IoArgsEventListener listener) throws IOException;
}
