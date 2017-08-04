package org.sxiong.yarpc.common.util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.sxiong.yarpc.common.constant.Constants;

import java.util.List;

/**
 * Created by sxiong on 7/29/17.
 */
public class ZookeeperPathUtils {
    // eg:"/yarpc/service/org.sxiong.yarpc.demo.api.DemoService/127.0.0.1:2181"
    private static final int PROVIDER_INFO_NODE_DEPTH = 4;

    // eg:"/yarpc/service/org.sxiong.yarpc.demo.api.DemoService"
    private static final int SERVICE_NAME_ZK_PATH_DEPTH = 3;

    public static String formatProviderPath(String serviceName, String ip, int port) {
        StringBuilder path = new StringBuilder();

        String serviceZkPath = String.format(Constants.SERVICE_ZK_PATH_FORMAT, serviceName);
        path.append(serviceZkPath);
        path.append(Constants.ZOOKEEPER_PATH_SEPARATOR);

        path.append(ip);
        path.append(":");
        path.append(port);

        return path.toString();
    }

    public static boolean isServiceProviderNode(String nodePath){
        return zkNodeDepth(nodePath) == PROVIDER_INFO_NODE_DEPTH;
    }

    public static int zkNodeDepth(String nodePath){
        if (Strings.isNullOrEmpty(nodePath)||Constants.ZOOKEEPER_PATH_SEPARATOR.equals(nodePath)){
            return 0;
        }
        if (!nodePath.startsWith(Constants.ZOOKEEPER_PATH_SEPARATOR)){
            throw new IllegalArgumentException("zookeeper path shou start with " + Constants.ZOOKEEPER_PATH_SEPARATOR);
        }
        String pathWithoutFirstSlash = nodePath.substring(1);
        return Iterables.size(Splitter.on(Constants.ZOOKEEPER_PATH_SEPARATOR).split(pathWithoutFirstSlash));
    }

    public static String getServiceNameFromProviderZkPath(String providerZkPath){
        String pathWithoutFirstSlash = providerZkPath.substring(1);
        List<String> parts = Lists.newArrayList(Splitter.on(Constants.ZOOKEEPER_PATH_SEPARATOR).split(pathWithoutFirstSlash));
        return parts.get(SERVICE_NAME_ZK_PATH_DEPTH - 1);//list index start with 0
    }
}
