package org.example.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("E:\\java\\netty_study\\a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\java\\netty_study\\a2.jpg");

        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        destCh.transferFrom(sourceCh,0,sourceCh.size());
        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
