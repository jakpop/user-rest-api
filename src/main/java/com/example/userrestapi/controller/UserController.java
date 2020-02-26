package com.example.userrestapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import com.example.userrestapi.repository.UserRepository;
import com.example.userrestapi.domain.User;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    @Autowired
    UserRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findUsers(@RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "surname", required = false) String surname,
                                                      @RequestParam(value = "email", required = false) String email) {

        List<User> users = repository.findByParams(name, surname, email);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = repository.findById(id);

        if (repository.findById(id) != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = repository.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        boolean wasUpdated = repository.update(id, updatedUser);

        if (wasUpdated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean wasDeleted = repository.delete(id);

        if (wasDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
