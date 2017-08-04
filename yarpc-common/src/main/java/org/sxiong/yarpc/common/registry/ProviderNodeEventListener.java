package org.sxiong.yarpc.common.registry;

import com.google.common.base.Strings;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.constant.Constants;
import org.sxiong.yarpc.common.util.ZookeeperPathUtils;

/**
 * Created by sxiong on 7/29/17.
 */

/**
 * 节点变化事件
 */
public class ProviderNodeEventListener implements TreeCacheListener {
    private static final Logger logger = LoggerFactory.getLogger(ProviderNodeEventListener.class);

    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        logger.info("curator event received:"+treeCacheEvent);

        if (!isServiceProviderNodeChangeEvent(treeCacheEvent)){
            return;
        }
        String serviceName = ZookeeperPathUtils.getServiceNameFromProviderZkPath(treeCacheEvent.getData().getPath());
        if (Strings.isNullOrEmpty(serviceName)){
            return;
        }
        ProviderStateListenerManager.getInstance().onProviderChange(serviceName);
    }

    private boolean isServiceProviderNodeChangeEvent(TreeCacheEvent event){
        if (event==null||event.getData()==null){
            return false;
        }

        String nodePath = event.getData().getPath();
        if (!nodePath.startsWith(Constants.SERVICE_ZK_PATH_PREFIX)){
            return false;
        }

        if (!ZookeeperPathUtils.isServiceProviderNode(nodePath)){
            return false;
        }

        TreeCacheEvent.Type type = event.getType();
        if (type == TreeCacheEvent.Type.NODE_ADDED || type == TreeCacheEvent.Type.NODE_REMOVED){
            return true;
        }
        return false;
    }
}
