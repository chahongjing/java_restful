package com.zjy;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Hello world!
 */
public class App {
    /**
     * 通过服务寄宿在GrizzlyHttpServerFactory来提供服务
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
//        Map<String, String> params = new HashMap<>();
//        params.put("name", "曾军毅");
//        params.put("age", "1");
//        Map<String, String> files = new HashMap<>();
//        files.put("file", "d://b.txt");
//        String s = HttpHelper.doGet("http://localhost:8080/api/rest/hello/123", params);
//        String ss = HttpHelper.doPost("http://localhost:8080/api/rest/hello/testpost", params);
//        String sss = HttpHelper.doPost("http://localhost:8080/api/rest/hello/testpost1", params, files);
        ResourceConfig rc = new ResourceConfig().packages(MyResources.class.getPackage().getName())
                .register(JacksonJsonProvider.class).register(MultiPartFeature.class);
        // String path = PropertiesHelper.getInstance().getProperties("serviceUrl");
        String path = Utils.getProperty("serviceUrl");
        System.out.println(MessageFormat.format("restful服务地址为：{0}/hello", path));
        GrizzlyHttpServerFactory.createHttpServer(URI.create(path), rc);
        while (true) {
            System.out.println(new Date());
            Thread.sleep(5000);
        }
    }
}
