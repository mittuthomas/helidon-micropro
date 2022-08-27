package com.example.helidon.micro;


import com.example.helidon.micro.openapi.BookingController;


import com.example.helidon.micro.config.ConfigTestController;


import com.example.helidon.micro.resilient.ResilienceController;


import com.example.helidon.micro.metric.MetricController;


import com.example.helidon.micro.client.ClientController;

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
        classes.add(HelloController.class);

        classes.add(BookingController.class);


        classes.add(ConfigTestController.class);


        classes.add(ResilienceController.class);


        classes.add(MetricController.class);


        classes.add(ClientController.class);


        return classes;
    }
}
