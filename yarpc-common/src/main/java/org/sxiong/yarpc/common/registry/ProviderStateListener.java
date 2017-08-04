package org.sxiong.yarpc.common.registry;

/**
 * Created by sxiong on 7/29/17.
 */
public interface ProviderStateListener {
    void onProviderChange(String serviceName);
}
