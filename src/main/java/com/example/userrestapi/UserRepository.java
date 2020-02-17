package com.example.userrestapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    public User create(User user) {
        users.add(user);
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

    public void updateIfExists(User original, User updated) {
        original.setId(updated.getId());
        original.setName(updated.getName());
        original.setSurname(updated.getSurname());
        original.setEmail(updated.getEmail());
    }

    public boolean containsUserWithId(Long id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }
}
