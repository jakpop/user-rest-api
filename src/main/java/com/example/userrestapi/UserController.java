package com.example.userrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    @Autowired
    UserRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findUsers(@RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "surname", required = false) String surname,
                                                      @RequestParam(value = "email", required = false) String email) {

        List<User> users = new ArrayList<User>();

        if (name != null) {
            users = repository.findByName(name);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        if (surname != null) {
            users = repository.findBySurname(surname);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        if (email != null) {
            users = repository.findByEmail(email);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        users = repository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = repository.findById(id);

        if (repository.containsUserWithId(id)) {
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
