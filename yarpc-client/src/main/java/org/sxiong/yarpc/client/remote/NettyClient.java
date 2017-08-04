package org.sxiong.yarpc.client.remote;

import com.google.common.util.concurrent.SettableFuture;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.entity.ProviderInfo;
import org.sxiong.yarpc.common.entity.RemoteRequest;
import org.sxiong.yarpc.common.entity.RemoteResponse;
import org.sxiong.yarpc.common.handler.NettyDecoder;
import org.sxiong.yarpc.common.handler.NettyEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by sxiong on 7/29/17.
 */
public class NettyClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private ProviderInfo providerInfo;

    public NettyClient(ProviderInfo providerInfo) {
        this.providerInfo = providerInfo;
    }

    public RemoteResponse send(RemoteRequest request) throws InterruptedException, TimeoutException, ExecutionException {
        logger.info("send remote request!");

        final SettableFuture<RemoteResponse> future = SettableFuture.create();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyDecoder(),new NettyEncoder(),new NettyClientHandler(future));
                        }
                    });
            ChannelFuture f = bootstrap.connect(providerInfo.getAddress(),providerInfo.getPort()).sync();
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    logger.info("client connect success!!!");
                }
            });
            ChannelFuture writeFuture = f.channel().writeAndFlush(request);
            writeFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    logger.info("client write complete");
                }
            });

            return future.get(1000, TimeUnit.MILLISECONDS);
        }finally {
            bossGroup.shutdownGracefully();
        }
    }
}
