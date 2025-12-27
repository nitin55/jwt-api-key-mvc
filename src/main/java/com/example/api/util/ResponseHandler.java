package com.example.api.util;

import javax.ws.rs.core.Response;
import java.util.Collections;

public class ResponseHandler {

    public static Response generate(
            int code, boolean success,
            String message, Object data) {

        return Response.status(code)
                .entity(new ApiResponseBody(
                        success, message,
                        data == null ? Collections.emptyMap() : data
                ))
                .build();
    }

    public static Response ok(String m, Object d) {
        return generate(200, true, m, d);
    }

   public static Response notFound(String message) {
    return generate(404, false, message, null);
  }
    public static Response created(String m, Object d) {
        return generate(201, true, m, d);
    }

    public static Response noContent() {
        return Response.status(204).build();
    }

    public static Response badRequest(String m) {
        return generate(400, false, m, null);
    }

    public static Response unauthorized(String m) {
        return generate(401, false, m, null);
    }

    public static Response serverError(String m) {
        return generate(500, false, m, null);
    }

    public static class ApiResponseBody {
        public boolean success;
        public String message;
        public Object data;

        public ApiResponseBody(
                boolean success,
                String message,
                Object data) {

            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}

