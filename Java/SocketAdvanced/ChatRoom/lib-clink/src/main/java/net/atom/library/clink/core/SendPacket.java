package net.atom.library.clink.core;

/**
 * 发送的包定义
 */
public abstract class SendPacket extends Packet{
    private boolean isCanceled;
    public abstract byte[] bytes();

    public boolean isCanceled() {
        return isCanceled;
    }
}
