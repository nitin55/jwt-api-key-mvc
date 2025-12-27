package com.example.api.controller;

import com.example.api.model.User;
import com.example.api.service.UserService;
import com.example.api.util.ResponseHandler;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService service = new UserService();

    @GET
    public Response getUsers() {
        return ResponseHandler.ok(
                "Users fetched",
                service.getAllUsers()
        );
    }

@POST
@Path("/add")
public Response create(@Valid User user) {
    service.addUser(user);
    return ResponseHandler.created("User created", user);
}

@POST
@Path("/update/{id}")
public Response updateUser(
        @PathParam("id") Long id,
        @Valid User user) {

    User existing = service.getById(id);
    if (existing == null) {
        return ResponseHandler.notFound("User not found");
    }

    existing.setName(user.getName());
    existing.setEmail(user.getEmail());
    service.updateUser(existing);

    return ResponseHandler.ok("User updated successfully", existing);
}


    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.deleteUser(id);
            return ResponseHandler.ok("User deleted successfully", null);
       // return ResponseHandler.noContent();
    }
}

