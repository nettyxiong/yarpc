package org.sxiong.yarpc.client.proxy;

import com.google.common.reflect.AbstractInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.client.provider.ServiceProviderManager;
import org.sxiong.yarpc.client.remote.NettyClient;
import org.sxiong.yarpc.common.entity.ProviderInfo;
import org.sxiong.yarpc.common.entity.RemoteRequest;
import org.sxiong.yarpc.common.entity.RemoteResponse;


import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by sxiong on 7/29/17.
 */
public class ServiceProxy extends AbstractInvocationHandler{
    private static final Logger logger = LoggerFactory.getLogger(ServiceProxy.class);
    private String serviceName;

    public ServiceProxy(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected Object handleInvocation(Object o, Method method, Object[] objects) throws Throwable {
        ProviderInfo providerInfo = ServiceProviderManager.getProviderInfo(serviceName);

        RemoteRequest request = new RemoteRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setServiceName(serviceName);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setArguments(objects);

        NettyClient client = new NettyClient(providerInfo);
        RemoteResponse response = client.send(request);

        return response.getResponseValue();
    }
}
