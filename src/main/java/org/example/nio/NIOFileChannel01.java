package org.example.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "hello,LEO";
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\java\\netty_study\\file01.txt");

        //通过fileOutPutStream 获取对应的FIleChannel
        //这个fileChannel 真实类型是 fileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();


        fileChannel.write(byteBuffer);
        fileOutputStream.close();



    }
}
