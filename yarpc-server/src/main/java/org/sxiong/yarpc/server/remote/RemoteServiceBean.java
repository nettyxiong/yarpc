package org.sxiong.yarpc.server.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.server.RemoteServiceServer;

/**
 * Created by sxiong on 7/27/17.
 */
public class RemoteServiceBean {
    private static final Logger logger = LoggerFactory.getLogger(RemoteServiceBean.class);

    private String serviceName;
    private Object serviceImpl;


    public void init(){
        RemoteServiceServer.registryService(serviceName,serviceImpl);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
    }
}
