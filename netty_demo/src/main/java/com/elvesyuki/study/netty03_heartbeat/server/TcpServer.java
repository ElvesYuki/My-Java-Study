package com.elvesyuki.study.netty03_heartbeat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 下午2:22
 * @description：
 * @modified By：
 */
public class TcpServer {

    private int port;

    private ServerHandlerInitializer serverHandlerInitializer;

    public TcpServer(int port) {
        this.port = port;
        this.serverHandlerInitializer = new ServerHandlerInitializer();
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(this.serverHandlerInitializer);

            ChannelFuture future = bootstrap.bind(port).sync();

            System.out.println("Server start listen at " + port);
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 2222;
        new TcpServer(port).start();
    }

}
