package com.example.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {

    public static void main(String[] args) throws Exception {

        // Jersey config
        ResourceConfig config = new ResourceConfig()
                .packages("com.example.api")
                .register(JacksonFeature.class)
                .register(org.glassfish.jersey.server.validation.ValidationFeature.class);

        // Jersey servlet
        ServletHolder jerseyServlet =
                new ServletHolder(new ServletContainer(config));

        // Jetty context
        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/api");
        context.addServlet(jerseyServlet, "/*");

        // Server
        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}

