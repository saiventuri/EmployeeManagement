package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Users.
 *
 * @author sai praveen venturi
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Returns User with the given user name.
     *
     * @param name the name of the user to be fetched.
     * @return the user with the given user name if present else null.
     */
    Optional<User> findByName(String name);
}
