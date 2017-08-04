package org.sxiong.yarpc.common.registry;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by sxiong on 7/29/17.
 */
public class ProviderStateListenerManager implements ProviderStateListener {
    private static ProviderStateListenerManager instance = new ProviderStateListenerManager();

    public static ProviderStateListenerManager getInstance(){
        return instance;
    }
    private final List<ProviderStateListener> listeners = Lists.newArrayList();

    public void registeryProviderStateListener(ProviderStateListener listener){
        listeners.add(listener);
    }

    public void onProviderChange(String serviceName) {
        for (ProviderStateListener listener:listeners){
            listener.onProviderChange(serviceName);
        }
    }
}
