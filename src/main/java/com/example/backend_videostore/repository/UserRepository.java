package com.example.backend_videostore.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.backend_videostore.model.User;

/*
  UserRepository is a Spring Data MongoDB repository for performing
  CRUD operations on the "users" collection.

  It extends MongoRepository<User, String>, which provides:
  - Standard CRUD methods (e.g., save, findAll, findById, deleteById)
  - Integration with MongoDB based on User's ID (String type)
*/
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /*
      Custom method to find a user by their email address.
      Returns an Optional<User>, which can be empty if no match is found.
      This method is used during login to authenticate the user.
    */
    Optional<User> findByEmail(String email);
}
