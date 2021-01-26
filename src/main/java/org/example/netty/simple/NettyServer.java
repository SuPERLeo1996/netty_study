package org.example.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName NettyServer
 * @Description
 * @Author Leo
 * @Date 2020/11/17 16:57
 */

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup bossEventLoop = new NioEventLoopGroup(1);
        NioEventLoopGroup workerEventLoop = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossEventLoop, workerEventLoop)
                    //使用NioSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动链接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //给workerGroup的EventLoop 对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("......... server is ready");

            //绑定一个端口吧并且同步，生成了一个ChannelFuture对象
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossEventLoop.shutdownGracefully();
            workerEventLoop.shutdownGracefully();

        }


    }
}
