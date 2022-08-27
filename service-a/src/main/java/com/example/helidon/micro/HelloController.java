package com.example.helidon.micro;

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

        return ip + "Hello World";
    }
}
