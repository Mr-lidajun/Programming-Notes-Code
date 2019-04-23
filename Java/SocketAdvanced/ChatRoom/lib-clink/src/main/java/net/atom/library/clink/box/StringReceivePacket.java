package net.atom.library.clink.box;

import net.atom.library.clink.core.ReceivePacket;

public class StringReceivePacket extends ReceivePacket {

    private byte[] buffer;
    private int position;

    public StringReceivePacket(int len) {
        this.buffer = new byte[len];
        length = len;
    }

    @Override
    public void save(byte[] bytes, int count) {
        System.arraycopy(bytes, 0, buffer, position, count);
        position += count;
    }

    public String string() {
        return new String(buffer);
    }
}
