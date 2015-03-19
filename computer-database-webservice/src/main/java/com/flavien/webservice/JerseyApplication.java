package com.flavien.webservice;

import org.glassfish.jersey.server.ResourceConfig;

import com.flavien.webservice.impl.CompanyWebserviceImpl;
import com.flavien.webservice.impl.ComputerWebserviceImpl;

public class JerseyApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public JerseyApplication () {
        register(ComputerWebserviceImpl.class);
        register(CompanyWebserviceImpl.class);
    }
}