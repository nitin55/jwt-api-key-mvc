package com.example.api.exception;

import com.example.api.util.ResponseHandler;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;

@Provider
public class ValidationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException ex) {
        String msg =
                ex.getConstraintViolations()
                  .iterator()
                  .next()
                  .getMessage();

        return ResponseHandler.badRequest(msg);
    }
}

