package com.elvesyuki.study.netty02_helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 上午11:25
 * @description：
 * @modified By：
 */
public class AppServerHello {

    private int port;

    public AppServerHello(int port) {
        this.port = port;
    }

    public void run() throws Exception {

        //Netty的Reactor线程池，初始化了一个NioEventLoop数组，用来处理I/O操作,如接受新的连接和读/写数据
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //用于启动NIO服务
            ServerBootstrap b = new ServerBootstrap();

            b.group(group)
                    //通过工厂方法设计模式实例化一个channel
                    .channel(NioServerSocketChannel.class)
                    //设置监听端口
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel,用于把许多自定义的处理类增加到pipline上来
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //配置childHandler来通知一个关于消息处理的InfoServerHandler实例
                            ch.pipeline().addLast(new HandlerServerHello());
                        }
                    });

            //绑定服务器，该实例将提供有关IO操作的结果或状态的信息
            ChannelFuture channelFuture = b.bind().sync();
            System.out.println("在" + channelFuture.channel().localAddress()+"上开启监听");
            //阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
            channelFuture.channel().closeFuture().sync();
        } finally {
            //关闭EventLoopGroup并释放所有资源，包括所有创建的线程
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new AppServerHello(18080).run();
    }


}
