package org.sxiong.yarpc.client.remote;

import com.google.common.util.concurrent.SettableFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.entity.RemoteResponse;

/**
 * Created by sxiong on 7/29/17.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RemoteResponse>{

    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    private SettableFuture<RemoteResponse> future;

    public NettyClientHandler(SettableFuture<RemoteResponse> future) {
        this.future = future;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RemoteResponse msg) throws Exception {
        future.set(msg);
    }
}
