package com.elvesyuki.study.netty02_helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 上午11:14
 * @description：
 * @modified By：
 */
public class AppClientHello {

    private final String host;

    private final int port;

    public AppClientHello(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {

        // I/O线程池
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 客户端辅助启动类
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    //实例化一个Channel
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    //进行通道初始化配置
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //添加我们自定义的Handler
                            socketChannel.pipeline().addLast(new HandlerClientHello());
                        }
                    });
            //连接到远程节点；等待连接完成
            ChannelFuture future = bs.connect().sync();

            //发送消息到服务器端，编码格式是utf-8
            future.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Wrold", CharsetUtil.UTF_8));

            //阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
            future.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {

        new AppClientHello("127.0.0.1", 18080).run();

    }

}
