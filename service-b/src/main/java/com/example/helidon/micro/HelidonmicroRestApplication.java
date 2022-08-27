package com.example.helidon.micro;


import com.example.helidon.micro.client.ServiceController;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApplicationPath("/data")

@ApplicationScoped
public class HelidonmicroRestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();

        // resources

        classes.add(ServiceController.class);


        return classes;
    }
}
