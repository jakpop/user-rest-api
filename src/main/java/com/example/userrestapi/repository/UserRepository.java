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

        if (matchingUser.isPresent()) {
            return matchingUser.get();
        }

        return null;
    }

    public List<User> findByParams(String name, String surname, String email) {

        if ((name != null) && (surname != null) && (email != null)) {
            return users.stream().filter(user -> user.getName().equals(name) && user.getSurname().equals(surname) && user.getEmail().equals(email)).collect(Collectors.toList());
        }
        if ((name != null) && (surname != null)) {
            return users.stream().filter(user -> user.getName().equals(name) && user.getSurname().equals(surname)).collect(Collectors.toList());
        }
        if ((name != null) && (email != null)) {
            return users.stream().filter(user -> user.getName().equals(name) && user.getEmail().equals(email)).collect(Collectors.toList());
        }
        if ((surname != null) && (email != null)) {
            return users.stream().filter(user -> user.getSurname().equals(surname) && user.getEmail().equals(email)).collect(Collectors.toList());
        }
        if (name != null) {
            return users.stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        }
        if (surname != null) {
            return users.stream().filter(user -> user.getSurname().equals(surname)).collect(Collectors.toList());
        }
        if (email != null) {
            return users.stream().filter(user -> user.getEmail().equals(email)).collect(Collectors.toList());
        }

        return users;
    }

    public boolean delete(Long id) {
        return users.removeIf(user -> user.getId() == id);
    }

    public boolean update(Long id, User updated) {
        if (updated == null) {
            return false;
        }
        if (findById(id) != null) {
            User original = findById(id);
            updateIfExists(original, updated);
            return true;
        }
        return false;
    }

    public void updateIfExists(User original, User updated) {
        original.setName(updated.getName());
        original.setSurname(updated.getSurname());
        original.setEmail(updated.getEmail());
    }
}
