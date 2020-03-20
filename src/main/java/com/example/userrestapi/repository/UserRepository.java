package com.example.userrestapi.repository;

import com.example.userrestapi.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByNameAndSurnameAndEmail(@Param("name") Optional<String> name, @Param("surname") Optional<String> surname, @Param("email") Optional<String> email);
}
