package com.example.api.exception;


import com.example.api.util.ResponseHandler;


import javax.validation.ConstraintViolationException;

import javax.ws.rs.core.Response;

import javax.ws.rs.ext.ExceptionMapper;

import javax.ws.rs.ext.Provider;


@Provider

public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {


    @Override

    public Response toResponse(Throwable exception) {


        // Handle Bean Validation errors

        if (exception instanceof ConstraintViolationException) {

            ConstraintViolationException ex = (ConstraintViolationException) exception;

            StringBuilder sb = new StringBuilder();

            ex.getConstraintViolations().forEach(cv -> 

                sb.append(cv.getPropertyPath())

                  .append(": ")

                  .append(cv.getMessage())

                  .append("; ")

            );

            return ResponseHandler.badRequest(sb.toString());

        }


        // Handle null request body (e.g., hitting POST with empty body)

        if (exception instanceof NullPointerException || exception.getMessage().contains("cannot invoke")) {

            return ResponseHandler.badRequest("Request body cannot be null or missing required fields");

        }

        // Handle JSON parse errors (invalid JSON)
        if (exception.getMessage() != null &&
                (exception.getMessage().contains("Unexpected character") ||
                 exception.getMessage().contains("Unrecognized field"))) {
            return ResponseHandler.badRequest("Invalid JSON format or unknown fields");
        }


        // Fallback for other exceptions

        exception.printStackTrace(); // log for debugging

        return ResponseHandler.serverError("Internal server error: " + exception.getMessage());

    }

}
