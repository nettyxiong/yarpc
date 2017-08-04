package org.sxiong.yarpc.client.provider;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.entity.ProviderInfo;
import org.sxiong.yarpc.common.registry.ProviderStateListenerManager;
import org.sxiong.yarpc.common.registry.RegistryManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sxiong on 7/29/17.
 */
public class ServiceProviderManager {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderManager.class);

    //核心数据结构,Key->服务名,Value->服务节点集合
    private static Map<String,Set<ProviderInfo>> serviceServerMap = new ConcurrentHashMap<String, Set<ProviderInfo>>();

    //类加载时启动
    static {
        try{
            bootstarp();
        }catch (Exception e){
            logger.error("client start error:"+e.getMessage());
            System.exit(1);
        }
    }

    public static void bootstarp() throws Exception {
        ProviderStateListenerManager.getInstance().registeryProviderStateListener(new ClientProviderStateListener());
        RegistryManager.start();
    }

    //随机选取一个节点,负载均衡
    public static ProviderInfo getProviderInfo(String serviceName){
        Set<ProviderInfo> providerInfoSet = serviceServerMap.get(serviceName);

        if(CollectionUtils.isEmpty(providerInfoSet)){
            throw new RuntimeException("no service provider found in registry");
        }

        List<ProviderInfo> providerInfoList = Lists.newArrayList(providerInfoSet);
        int randomIndex = new Random().nextInt(providerInfoList.size());
        return providerInfoList.get(randomIndex);
    }

    //从zookeeper加载服务下的所有节点
    public static void initServerListOfService(String serviceName){
        logger.debug("client start load " + serviceName + " service provider list from zk");

        List<ProviderInfo> providerInfos = RegistryManager.loadServiceListOfService(serviceName);
        logger.debug("client load "+serviceName+" service provider list from zk: "+providerInfos);

        Set<ProviderInfo> providerInfoSet = new HashSet<ProviderInfo>(providerInfos);
        serviceServerMap.put(serviceName,providerInfoSet);

        logger.debug("client serviceServerMap after load:" + serviceServerMap);
    }
}
