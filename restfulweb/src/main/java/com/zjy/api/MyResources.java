package com.zjy.api;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.zjy.entities.UserInfo;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chahongjing on 2017/2/4.
 */
@Path("/hello")
public class MyResources {

    /**
     * 通过两种方式启动restful服务
     * 1. 直接启动web服务，调用地址为http://ip:port/restful/rest/hello，其中hello为本类的Path路径，如上
     * 2. 通过服务寄宿在GrizzlyHttpServerFactory来提供服务，如下的main函数
     *
     *
     *
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
        String path = "http://localhost:8080/api/rest";
        // GrizzlyHttpServerFactory.createHttpServer(URI.create(path), rc);
        while (true) {
            System.out.println(new Date());
            Thread.sleep(5000);
        }
    }

    @GET
    public String sayHello() {
        return "Hello World!";
    }

    @GET
    @Path("/returnentity")
    // 返回类型
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo returnEntity() {
        UserInfo user = new UserInfo();
        user.setUserName("曾军毅");
        user.setPassword("abc123");
        return user;
    }

    @GET
    @Path("/test")
    // 可接受类型
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo test() {
        //Client client = ClientBuilder.newClient();
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class).register(MultiPartFeature.class);// 注册json 支持
        String path = "http://localhost:8080/api/rest";
        WebTarget target = client.target(path + "/hello/returnentity");
        Response response = target.request().get();
        UserInfo user = response.readEntity(UserInfo.class);
        System.out.println(user.getUserName());

        response = target.request(MediaType.APPLICATION_JSON).get();
        user = response.readEntity(UserInfo.class);
        System.out.println(user.getUserName());
        response.close();
        return user;
    }

    @GET
    @Path("/testGet/{id}")
    // 可接受类型
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String testGet(@QueryParam("name") String name, @PathParam("id") int id, @Context HttpServletRequest request) {
        System.out.println(name + "_" + String.valueOf(id));
        return "testGet success";
    }

    @POST
    @Path("/testPost")
    // 可接受类型
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String testPost(@FormParam("name") String name, @FormParam("age") int age, @Context HttpServletRequest request) {
        return "testPost success";
    }

    @POST
    @Path("/testPostWithFile")
    // 可接受类型
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public String testPostWithFile(FormDataMultiPart form, @Context HttpServletRequest request) {
        for (Map.Entry<String, List<FormDataBodyPart>> entry : form.getFields().entrySet()) {
            System.out.println("key:" + entry.getKey());
            for (FormDataBodyPart part : entry.getValue()) {
                if (part.getContentDisposition().getFileName() != null) {
                    System.out.println("value:" + part.getContentDisposition().getFileName());
                } else {
                    System.out.println("value:" + part.getEntityAs(String.class));
                }
            }
        }
        // FormDataMultiPart form
        // @FormDataParam("file") InputStream fileInputStream
        // @FormDataParam("file") FormDataContentDisposition file
        return "testPost with files success";
    }
}