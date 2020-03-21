package com.example.userrestapi.service;

import com.example.userrestapi.domain.User;
import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(String id);
    List<User> findByParams(String name, String surname, String email);
    User create(User user);
    boolean update(String id, User user);
    boolean delete(String id);

    void updateIfExists(User original, User updated);
}
