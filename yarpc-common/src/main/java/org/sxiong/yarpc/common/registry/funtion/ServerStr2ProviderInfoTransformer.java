package org.sxiong.yarpc.common.registry.funtion;


import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.sxiong.yarpc.common.entity.ProviderInfo;

import java.util.List;

/**
 * Created by sxiong on 7/29/17.
 */
public class ServerStr2ProviderInfoTransformer implements Function<String,ProviderInfo> {
    public static final ServerStr2ProviderInfoTransformer instance = new ServerStr2ProviderInfoTransformer();

    @Override
    public ProviderInfo apply(String serverAddress) {
        if (Strings.isNullOrEmpty(serverAddress)){
            return null;
        }
        List<String> splitStrs = Lists.newArrayList(Splitter.on(":").split(serverAddress));
        if (CollectionUtils.isNotEmpty(splitStrs) && splitStrs.size() == 2){
            return new ProviderInfo(splitStrs.get(0),Integer.valueOf(splitStrs.get(1)));
        }
        return null;
    }
}
