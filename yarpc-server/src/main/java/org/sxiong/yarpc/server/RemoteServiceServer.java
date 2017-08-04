package org.sxiong.yarpc.server;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.config.ConfigManager;
import org.sxiong.yarpc.common.constant.Constants;
import org.sxiong.yarpc.common.registry.RegistryManager;
import org.sxiong.yarpc.server.remote.NettyServer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sxiong on 7/27/17.
 */
public class RemoteServiceServer {
    private static final Logger logger = LoggerFactory.getLogger(RemoteServiceServer.class);

    private static volatile boolean isRunning = false;

    private static String ipAddress = ConfigManager.getInstance().getProperty(Constants.SERVER_ADDRESS_IP_CONFIG_KEY);

    private static int port = Strings.isNullOrEmpty(ConfigManager.getInstance().getProperty(Constants.SERVER_ADDRESS_PORT_CONFIG_KEY))?Constants.SERVER_DEFAULT_PORT:Integer.valueOf(ConfigManager.getInstance().getProperty(Constants.SERVER_ADDRESS_PORT_CONFIG_KEY));

    private static ConcurrentHashMap<String,Object> serviceImplMap = new ConcurrentHashMap<String, Object>();

    static{
        try{
            bootstrap();
        }catch (Exception e){
            logger.error("Server start error:" + e.getMessage(), e);
            System.exit(1);
        }
    }

    public static void registryService(String serviceName,Object serviceImpl){
        serviceImplMap.putIfAbsent(serviceName,serviceImpl);
        RegistryManager.publishService(serviceName,ipAddress,port);
    }

    public static void bootstrap() throws Exception {
        logger.info("try bootstrap state:" + isRunning) ;
        if (!isRunning){
            synchronized (RemoteServiceServer.class){
                if (!isRunning){
                    doStart();
                }
            }
        }
        logger.info("started");
    }

    private static void doStart() throws Exception {
        logger.info("do start server");
        NettyServer.start(port);
        RegistryManager.start();
    }

    public static Object getActualServiceImpl(String serviceName){
        return serviceImplMap.get(serviceName);
    }
}
