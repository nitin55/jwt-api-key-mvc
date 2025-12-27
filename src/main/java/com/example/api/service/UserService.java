package com.example.api.service;

import com.example.api.dao.UserDao;
import com.example.api.model.User;
import javax.ws.rs.BadRequestException;
import java.util.List;

public class UserService {

    private final UserDao dao = new UserDao();

    public List<User> getAllUsers() {
        return dao.getAll();
    }
    public User getById(Long id) {
    return dao.findById(id);
   }

    public void addUser(User user) {
        if (dao.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        dao.add(user);
    }

    public void updateUser(User user) {
        dao.update(user);
    }

    public void deleteUser(Long id) {
        dao.delete(id);
    }
}

