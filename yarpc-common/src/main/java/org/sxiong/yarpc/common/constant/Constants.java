package org.sxiong.yarpc.common.constant;

/**
 * Created by sxiong on 7/27/17.
 */
public class Constants {
    //server config
    public static final int SERVER_DEFAULT_PORT = 8088;
    public static final String SERVER_ADDRESS_IP_CONFIG_KEY = "server.address.ip";
    public static final String SERVER_ADDRESS_PORT_CONFIG_KEY = "server.address.port";

    //zookeeper config
    public static final String ZOOKEEPER_ADDRESS = "registry.zookeeper.address";
    public static final String ZOOKEEPER_PATH_SEPARATOR = "/";
    public static final String SERVICE_ZK_PATH_PREFIX = "/yarpc/service";
    public static final String SERVICE_ZK_PATH_FORMAT = SERVICE_ZK_PATH_PREFIX + "/%s";

}
