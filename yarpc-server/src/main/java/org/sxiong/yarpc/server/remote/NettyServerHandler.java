package org.sxiong.yarpc.server.remote;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.entity.RemoteRequest;
import org.sxiong.yarpc.common.entity.RemoteResponse;
import org.sxiong.yarpc.server.RemoteServiceServer;

import java.lang.reflect.Method;

/**
 * Created by sxiong on 7/27/17.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RemoteRequest>{
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RemoteRequest remoteRequest) throws Exception {

        Object actualServiceImpl = RemoteServiceServer.getActualServiceImpl(remoteRequest.getServiceName());

        if (actualServiceImpl!=null){
            RemoteResponse response = new RemoteResponse();
            response.setRequestId(remoteRequest.getRequestId());
            Class<?> serviceInterface = actualServiceImpl.getClass();
            Method method = serviceInterface.getMethod(remoteRequest.getMethodName(),remoteRequest.getParameterTypes());
            Object result = method.invoke(actualServiceImpl,remoteRequest.getArguments());

            logger.info("get result from server: "+ result);

            response.setResponseValue(result);

            channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    logger.info("server write back success");
                }
            });
        }
    }
}
