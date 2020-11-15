package org.example.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    //构造器
    //初始化工作
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将该listenChannel注册到Selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    //有事件处理
                    //遍历得到的SelectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectionKey
                        SelectionKey key = iterator.next();

                        //监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将sc注册到selector
                            sc.register(selector, SelectionKey.OP_READ);

                            System.out.println(sc.getRemoteAddress() + " 上线");
                        }

                        if (key.isReadable()) {
                            //通道发生read事件
                            //处理读
                            readData(key);
                        }
                        //当前的key删除
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待中");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void readData(SelectionKey key) {
        //先定义一个SocketChannel
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            if (count > 0) {
                //把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                System.out.println("from 客户端:" + msg);
                //像其他的客户端转发消息
                sendInfoToOtherClients(msg,channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        //遍历所有注册到selector上的SocketChannel
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();

            if (targetChannel instanceof SocketChannel && targetChannel != self) {

                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer的数据写入通道
                dest.write(buffer);
            }

        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }


}
