package org.sxiong.yarpc.server.remote;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.handler.NettyDecoder;
import org.sxiong.yarpc.common.handler.NettyEncoder;


/**
 * Created by sxiong on 7/27/17.
 */
public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private static volatile boolean isStarted = false;

    public static void start(int port){
        if (!isStarted){
            synchronized (NettyServer.class){
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();

                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup,workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new NettyDecoder(),new NettyEncoder(),new NettyServerHandler());
                            }
                        }).option(ChannelOption.SO_BACKLOG,128)
                        .childOption(ChannelOption.SO_KEEPALIVE,true);

                try{
                    ChannelFuture f = serverBootstrap.bind(port).sync();
                    f.addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isSuccess()){
                                isStarted = true;
                                logger.info("server started successful!");
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    logger.error("server started failed :"+e.getMessage(),e);
                }
            }
        }
    }
}
