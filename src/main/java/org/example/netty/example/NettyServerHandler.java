package org.example.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * @ClassName NettyServerHandler
 * @Description
 * @Author Leo
 * @Date 2020/11/17 17:16
 */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送的消息
     * @param ctx 上下文对象 含有管道pipeline 地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx =" + ctx);

        ByteBuf buf = (ByteBuf) msg;

        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }


    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //write() + flush() 将数据写入缓冲并刷新
        ctx.writeAndFlush("");
    }
}
