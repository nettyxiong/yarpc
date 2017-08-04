package org.sxiong.yarpc.common.registry;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.config.ConfigManager;
import org.sxiong.yarpc.common.constant.Constants;
import org.sxiong.yarpc.common.entity.ProviderInfo;
import org.sxiong.yarpc.common.registry.funtion.ServerStr2ProviderInfoTransformer;
import org.sxiong.yarpc.common.util.ZookeeperPathUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by sxiong on 7/27/17.
 */
public class RegistryManager{
    private static final Logger logger = LoggerFactory.getLogger(RegistryManager.class);

    private static CuratorFramework client;

    private static Executor curatorEventThreadPool = Executors.newFixedThreadPool(100);

    private static volatile boolean isStarted = false;

    public static void start() throws Exception {
        if (!isStarted){
            synchronized (RegistryManager.class){
                if (!isStarted){
                    String zookeeperAddress = ConfigManager.getInstance().getProperty(Constants.ZOOKEEPER_ADDRESS);
                    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
                    client = CuratorFrameworkFactory.newClient(zookeeperAddress,retryPolicy);
                    client.start();
                    TreeCache treeCache = TreeCache.newBuilder(client,Constants.SERVICE_ZK_PATH_PREFIX).setCacheData(false).build();
                    treeCache.getListenable().addListener(new ProviderNodeEventListener(),curatorEventThreadPool);
                    treeCache.start();

                    isStarted = client.blockUntilConnected(1000, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    public static boolean publishService(String serviceName,String ip,int port){
        if (!isStarted){
            throw new IllegalStateException("registry zk is not started...");
        }

        if (Strings.isNullOrEmpty(ip)){
            throw new IllegalArgumentException("local ip address is not configured yet");
        }

        String currentServerPath = ZookeeperPathUtils.formatProviderPath(serviceName,ip,port);
        try {
            String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(currentServerPath);
            if (Strings.isNullOrEmpty(result)){
                logger.error("error when add service to zk:" + result);
                return false;
            }
            return true;
        }catch (KeeperException.NodeExistsException nee) {
            logger.warn("target service " + currentServerPath +"already exists in zk");
            return true;
        }catch (Exception e) {
            logger.error("error when add service to zk:" + e.getMessage(),e);
            return false;
        }
    }

    public static List<ProviderInfo> loadServiceListOfService(String serviceName){
        if (!isStarted){
            throw new IllegalStateException("registry zk is not started...");
        }

        try{
            List<String> stringList = client.getChildren().watched().forPath(String.format(Constants.SERVICE_ZK_PATH_FORMAT,serviceName));
            List<ProviderInfo> providerInfos = Lists.transform(stringList, ServerStr2ProviderInfoTransformer.instance);
            return providerInfos;
        } catch (Exception e) {
            logger.error("error when getting server list from registry:"+e.getMessage(),e);
            return Collections.emptyList();
        }
    }
}
