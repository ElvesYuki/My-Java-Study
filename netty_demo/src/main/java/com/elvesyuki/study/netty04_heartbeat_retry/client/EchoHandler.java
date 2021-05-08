package com.elvesyuki.study.netty04_heartbeat_retry.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 上午11:58
 * @description：
 * @modified By：
 */
public class EchoHandler extends SimpleChannelInboundHandler<ByteBuf> {
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
