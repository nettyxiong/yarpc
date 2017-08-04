package org.sxiong.yarpc.client.proxy;

import com.google.common.reflect.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.client.provider.ServiceProviderManager;

/**
 * Created by sxiong on 7/29/17.
 */
public class ServiceProxyBeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProxyBeanFactory.class);

    public static Object getService(String serviceName) throws ClassNotFoundException{
        Class<?> service = Class.forName(serviceName);

        //加载可用的serviceName的服务节点到客户端Ｍap<serviceName,serverNodes>中
        ServiceProviderManager.initServerListOfService(serviceName);

        //返回代理实例,在ＳerviceＰroxy中完成网络请求的发起操作
        return Reflection.newProxy(service,new ServiceProxy(serviceName));
    }

}
