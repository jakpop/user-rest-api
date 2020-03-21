package com.example.userrestapi.service;

import com.example.userrestapi.domain.User;
import com.example.userrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        Optional<User> matchingUser = userRepository.findAll().stream().filter(user -> user.getId().equals(id)).findFirst();

        if (matchingUser.isPresent()) {
            return matchingUser.get();
        }

        return null;
    }

    @Override
    public List<User> findByParams(String name, String surname, String email) {

        Stream<User> userStream = userRepository.findAll().stream();

        if (name != null) {
            userStream = userStream.filter(user -> user.getName().equals(name));
        }
        if (surname != null) {
            userStream = userStream.filter(user -> user.getSurname().equals(surname));
        }
        if (email != null) {
            userStream = userStream.filter(user -> user.getEmail().equals(email));
        }

        return userStream.collect(Collectors.toList());
    }

    @Override
    public boolean update(String id, User updated) {
        User original = findById(id);

        if (updated == null) {
            return false;
        }
        if (original != null) {
            updateIfExists(original, updated);
            return true;
        }
        return false;
    }

    @Override
    public void updateIfExists(User original, User updated) {
        original.setName(updated.getName());
        original.setSurname(updated.getSurname());
        original.setEmail(updated.getEmail());
    }

    @Override
    public boolean delete(String id) {
        return userRepository.findAll().removeIf(user -> user.getId().equals(id));
    }
}
