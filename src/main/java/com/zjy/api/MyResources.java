package com.zjy.api;

import com.zjy.entities.UserInfo;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 * Created by chahongjing on 2017/2/4.
 */
@Path("/hello")
public class MyResources {

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
        Client client = ClientBuilder.newClient();
        //Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);// 注册json 支持
        WebTarget target = client.target("http://localhost:8080/api/rest/hello/users006");
        Response response = target.request().get();
        UserInfo user = response.readEntity(UserInfo.class);
        System.out.println(user.getUserName());
        response = target.request(MediaType.APPLICATION_JSON).get();
        user = response.readEntity(UserInfo.class);
        System.out.println(user.getUserName());
        response.close();
    }
}