package com.elvesyuki.study.netty04_heartbeat_retry.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 下午2:17
 * @description：<p>收到来自客户端的数据包后, 直接在控制台打印出来.</p>
 * @modified By：
 */
@ChannelHandler.Sharable
public class ServerBizHandler extends SimpleChannelInboundHandler<String> {

    private final String REC_HEART_BEAT = "I had received the heart beat!";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //处理收到的数据，并反馈消息到到客户端
        System.out.println("收到客户端发来的消息" + msg);
        // ctx.writeAndFlush(REC_HEART_BEAT);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Established connection with the remote client.");

        // do something

        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Disconnected with the remote client.");

        // do something

        ctx.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
