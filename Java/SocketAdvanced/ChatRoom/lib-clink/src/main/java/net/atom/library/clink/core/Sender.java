package net.atom.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 发送者
 */
public interface Sender extends Closeable {
    /**
     * 异步发送
     * @param args 发送的数据
     * @param listener 发送状态回调
     * @return
     */
    boolean sendAsync(IoArgs args, IoArgs.IoArgsEventListener listener) throws IOException;
}
