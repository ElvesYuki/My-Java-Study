package com.elvesyuki.study.netty03_heartbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 下午12:01
 * @description：TcpClient —— TCP连接的客户端：
 * @modified By：
 */
public class TcpClient {

    private String host;
    private int port;

    private Bootstrap bootstrap;

    /**
     * 将<code>Channel</code>保存起来, 可用于在其他非handler的地方发送数据
     */
    private Channel channel;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    public void  connect() {
        synchronized (bootstrap) {
            ChannelFuture future = bootstrap.connect(host, port);
            this.channel = future.channel();
        }
    }

    private void init() {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientHandlersInitializer(TcpClient.this));
    }

    public static void main(String[] args) {
        TcpClient tcpClient = new TcpClient("localhost", 2222);
        tcpClient.connect();
    }

}
