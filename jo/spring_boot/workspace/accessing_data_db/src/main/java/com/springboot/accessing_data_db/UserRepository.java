package com.springboot.accessing_data_db;


import org.springframework.data.repository.CrudRepository;

import com.springboot.accessing_data_db.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByName(String name);
}