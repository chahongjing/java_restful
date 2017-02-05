package com.zjy.api;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.zjy.baseframework.PropertiesHelper;
import com.zjy.entities.UserInfo;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Date;

/**
 * Created by chahongjing on 2017/2/4.
 */
@Path("/hello")
public class MyResources {

    public static void main(String[] args) throws InterruptedException {
        ResourceConfig rc = new ResourceConfig().packages(MyResources.class.getPackage().getName()).register(JacksonJsonProvider.class);
        String path = PropertiesHelper.getInstance().getProperties("serviceUrl");
        GrizzlyHttpServerFactory.createHttpServer(URI.create(path), rc);
        while(true) {
            System.out.println(new Date());
            Thread.sleep(1000);
        }
    }

    @GET
    // 可接受类型
    @Consumes(MediaType.TEXT_PLAIN)
    // 返回类型
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World!";
    }

    @GET
    @Path("/{param}")
    // 可接受类型
    @Consumes({MediaType.TEXT_PLAIN})
    // 返回类型
    @Produces({MediaType.APPLICATION_JSON})
    public UserInfo sayHelloToUTF8(@PathParam("param") String userName) {
        UserInfo user = new UserInfo();
        user.setUserName(userName);
        user.setPassword("password");
        return user;
    }

    @GET
    @Path("/test")
    // 可接受类型
    @Consumes({MediaType.TEXT_PLAIN})
    public void test() {
        //Client client = ClientBuilder.newClient();
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);// 注册json 支持
        String path = PropertiesHelper.getInstance().getProperties("serviceUrl");
        WebTarget target = client.target(path + "/hello/users006");
        Response response = target.request().get();
        UserInfo user = response.readEntity(UserInfo.class);
        System.out.println(user.getUserName());
        response = target.request(MediaType.APPLICATION_JSON).get();
        user = response.readEntity(UserInfo.class);
        System.out.println(user.getUserName());
        response.close();
    }
}