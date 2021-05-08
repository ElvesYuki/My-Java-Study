package com.elvesyuki.study.netty03_heartbeat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 下午2:19
 * @description：
 * @modified By：
 */
public class ServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline()
                // new IdleStateHandler(5, 0, 0)该handler代表如果在5秒内没有收到来自客户端的任何数据包（包括但不限于心跳包），将会主动断开与该客户端的连接。
                .addLast(new IdleStateHandler(10, 0, 0))
                .addLast(new ServerIdleStateTrigger())
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                .addLast(new LengthFieldPrepender(4))
                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                .addLast(new ServerBizHandler());
    }
}
