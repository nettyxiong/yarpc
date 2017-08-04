package org.sxiong.yarpc.client.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.registry.ProviderStateListener;

/**
 * Created by sxiong on 7/29/17.
 */
public class ClientProviderStateListener implements ProviderStateListener{
    private static final Logger logger = LoggerFactory.getLogger(ClientProviderStateListener.class);
    public void onProviderChange(String serviceName) {
        logger.info("client event listener received changed service:"+serviceName);
        ServiceProviderManager.initServerListOfService(serviceName);
    }
}
