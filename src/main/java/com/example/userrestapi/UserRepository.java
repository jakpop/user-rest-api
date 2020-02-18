package com.example.userrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        User testUser = new User();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                testUser = users.get(i);

            }
        }
        return testUser;
    }

    public List<User> findByName(String name) {
        List<User> usersWithName = new ArrayList<User>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                usersWithName.add(users.get(i));
            }
        }
        return usersWithName;
    }

    public List<User> findBySurname(String surname) {
        List<User> usersWithSurname = new ArrayList<User>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getSurname().equals(surname)) {
                usersWithSurname.add(users.get(i));
            }
        }
        return usersWithSurname;
    }

    public List<User> findByEmail(String email) {
        List<User> usersWithEmail = new ArrayList<User>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                usersWithEmail.add(users.get(i));
            }
        }
        return usersWithEmail;
    }

    public boolean delete(Long id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                return true;
            }
        }
        return false;
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
        original.setId(idGenerator.nextId());
        original.setName(updated.getName());
        original.setSurname(updated.getSurname());
        original.setEmail(updated.getEmail());
    }
}
