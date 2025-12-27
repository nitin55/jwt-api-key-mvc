package com.example.api.config;

import com.example.api.dao.ApiKeyDao;
import com.example.api.util.ResponseHandler;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class ApiKeyAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext ctx) {

        String apiKey = ctx.getHeaderString("X-API-KEY");

        if (apiKey == null || !ApiKeyDao.isValid(apiKey)) {
            ctx.abortWith(
                    ResponseHandler.unauthorized("Invalid API Key")
            );
        }
    }
}

