package com.fastkart.sellerservice.repository;

import com.fastkart.sellerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}