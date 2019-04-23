package net.atom.library.clink.box;

import net.atom.library.clink.core.SendPacket;

public class StringSendPacket extends SendPacket {
    private final byte[] bytes;

    public StringSendPacket(String msg) {
        this.bytes = msg.getBytes();
    }

    @Override
    public byte[] bytes() {
        return bytes;
    }
}
