package com.springboot.mariadb_test2.repo;

import java.util.List;
 
import org.springframework.data.repository.CrudRepository;
 
import com.springboot.mariadb_test2.model.Customer;
 
 
public interface CustomerRepository extends CrudRepository<Customer, Long>{
  List<Customer> findByLastName(String lastName);
  
}