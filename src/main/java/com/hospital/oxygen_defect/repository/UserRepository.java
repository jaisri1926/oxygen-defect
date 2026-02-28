package com.hospital.oxygen_defect.repository;

import com.hospital.oxygen_defect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Find user by username
    User findByUsername(String username);
    
    // Find user by username and password
    User findByUsernameAndPassword(String username, String password);
}