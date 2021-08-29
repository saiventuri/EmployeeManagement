package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Roles.
 *
 * @author sai praveen venturi
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Returns User with the given role name.
     *
     * @param name the name of the role to be fetched.
     * @return the user with the given role name if present else null.
     */
    Optional<Role> findByName(String name);
}
