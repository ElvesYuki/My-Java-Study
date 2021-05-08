package com.elvesyuki.study.netty02_helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 上午11:12
 * @description：通用handler，处理I/O事件
 * @modified By：
 */
@ChannelHandler.Sharable
public class HandlerClientHello extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * @Description  处理接收到的消息
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        System.out.println("接收到的消息" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * @Description  处理I/O事件的异常
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
