package com.zjy;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.zjy.api.MyResources;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by chahongjing on 2017/2/7.
 */
public class ApiApplication extends ResourceConfig {
    public ApiApplication() {
        //加载Resource
        register(MyResources.class);
        //注册数据转换器
        register(JacksonJsonProvider.class);
        register(MultiPartFeature.class);
    }
}
