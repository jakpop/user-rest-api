package com.example.userrestapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.userrestapi.domain.User;

@Repository
public class UserRepository {

    @Autowired
    IdGenerator idGenerator;

    private List<User> users = new ArrayList<User>();

    public User create(User user) {
        users.add(user);
        user.setId(idGenerator.nextId());
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(Long id) {
        Optional<User> matchingUser = users.stream().filter(user -> user.getId() == id).findFirst();

        User userWithId = new User();
        if (matchingUser.isPresent()) {
            userWithId = matchingUser.get();
        }

        return userWithId;
    }

    public List<User> findByName(String name) {
        return users.stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
    }

    public List<User> findBySurname(String surname) {
        return users.stream().filter(user -> user.getSurname().equals(surname)).collect(Collectors.toList());
    }

    public List<User> findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        return users.removeIf(user -> user.getId() == id);
    }

    public boolean update(Long id, User updated) {
        if (updated == null) {
            return false;
        }
        else {
            if (containsUserWithId(id)) {
                User original = findById(id);
                updateIfExists(original, updated);
                return true;
            }
        }
        return false;
    }

    public boolean containsUserWithId(Long id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void updateIfExists(User original, User updated) {
        original.setName(updated.getName());
        original.setSurname(updated.getSurname());
        original.setEmail(updated.getEmail());
    }
}
