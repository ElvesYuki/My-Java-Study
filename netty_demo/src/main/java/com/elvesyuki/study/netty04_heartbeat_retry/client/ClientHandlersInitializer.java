package com.elvesyuki.study.netty04_heartbeat_retry.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 上午11:54
 * @description：
 * @modified By：
 */
public class ClientHandlersInitializer extends ChannelInitializer<SocketChannel> {

    private ReconnectHandler reconnectHandler;

    private EchoHandler echoHandler;

    public ClientHandlersInitializer(TcpClient tcpClient) {
        echoHandler = new EchoHandler();
        reconnectHandler = new ReconnectHandler(tcpClient);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(this.reconnectHandler)
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                .addLast(new LengthFieldPrepender(4))
                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                .addLast(new Pinger())
                .addLast(new EchoHandler());
    }
}
