package com.example.api.dao;

import java.sql.*;

public class ApiKeyDao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/jwt_db";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static boolean isValid(String apiKey) {
        String sql =
                "SELECT active FROM api_keys WHERE api_key=?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, apiKey);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getBoolean("active");

        } catch (SQLException e) {
            return false;
        }
    }
}

