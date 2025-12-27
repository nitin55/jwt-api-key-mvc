package com.example.api.dao;

import com.example.api.model.User;

import java.sql.*;
import java.util.*;

public class UserDao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/jwt_db";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id,name,email FROM users";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error");
        }
        return users;
    }

    public void add(User user) {
        String sql =
                "INSERT INTO users(name,email) VALUES (?,?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error");
        }
    }

    public void update(User user) {
        String sql =
                "UPDATE users SET name=?, email=? WHERE id=?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setLong(3, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error");
        }
    }

public boolean existsByEmail(String email) {
    String sql = "SELECT 1 FROM users WHERE email=? LIMIT 1";

    try (Connection c = getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();

    } catch (SQLException e) {
        return false;
    }
}

public User findById(Long id) {
    String sql = "SELECT id,name,email FROM users WHERE id=?";
    try (Connection c = getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email")
            );
        }
        return null;
    } catch (SQLException e) {
        throw new RuntimeException("DB error");
    }
}

    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id=?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error");
        }
    }
}

