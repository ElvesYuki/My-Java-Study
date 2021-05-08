package com.elvesyuki.study.netty04_heartbeat_retry.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 上午11:44
 * @description：类ClientIdleStateTrigger也是一个Handler，只是重写了userEventTriggered方法，
 * 用于捕获IdleState.WRITER_IDLE事件（未在指定时间内向服务器发送数据），然后向Server端发送一个心跳包。
 * @modified By：
 */
public class ClientIdleStateTrigger extends ChannelInboundHandlerAdapter {

    public static final String HEART_BEAT = "heart beat!";

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();

            if (state == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(HEART_BEAT);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
