package com.ford.springbootstart.Repository;

import com.ford.springbootstart.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
