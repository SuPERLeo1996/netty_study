package org.example.netty.example;

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

        NioEventLoopGroup bossEventLoop = new NioEventLoopGroup();
        NioEventLoopGroup workerEventLoop = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossEventLoop,workerEventLoop)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(null);
                    }
                });

        System.out.println("......... server is ready");

        ChannelFuture cf = bootstrap.bind(6668).sync();

    }
}
