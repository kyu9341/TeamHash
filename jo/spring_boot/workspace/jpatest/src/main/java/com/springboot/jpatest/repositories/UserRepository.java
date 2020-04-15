package com.springboot.jpatest.repositories;

import com.springboot.jpatest.model.User;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
}