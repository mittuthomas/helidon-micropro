package com.example.helidon.micro;

import com.ObjectStorageService;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {

    @GET
    public String sayHello() {

        InetAddress localhost = null;
        String ip =">";
        try {
            localhost = InetAddress.getLocalHost();
            ip = localhost.getHostAddress().trim();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
       System.out.println(ip);
String output = null;
        try {
            output = ObjectStorageService.connect();//.main(new String[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip + " Hello World" +"\n"+ output;
    }
}
