package org.example.nio;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putInt(100);
        buffer.putLong(9L);
        buffer.putChar('爱');
        buffer.putShort((short) 4);

        buffer.flip();

        System.out.println();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
