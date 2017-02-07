package com.zjy.api;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.zjy.entities.UserInfo;
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
import java.net.URI;
import java.util.Date;

/**
 * Created by chahongjing on 2017/2/4.
 */
@Path("/hello")
public class MyResources {

    public static void main(String[] args) throws InterruptedException {
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
    public String test() {
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
        return "";
    }

    @GET
    @Path("/{id}")
    // 可接受类型
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String testGet(@QueryParam("name") String name, @PathParam("id") int id, @Context HttpServletRequest request) {
        System.out.println(name + "_" + String.valueOf(id));
        return "testGet success";
    }

    @POST
    @Path("/testpost")
    // 可接受类型
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String testPost(@FormParam("name") String name, @FormParam("age") int age, @Context HttpServletRequest request) {
        return "testPost success";
    }

    @POST
    @Path("/testpost1")
    // 可接受类型
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public String testPost1(FormDataMultiPart form, @Context HttpServletRequest request) {
        // FormDataMultiPart form
        // @FormDataParam("file") InputStream fileInputStream
        // @FormDataParam("file") FormDataContentDisposition file
        return "testPost with files success";
    }
}