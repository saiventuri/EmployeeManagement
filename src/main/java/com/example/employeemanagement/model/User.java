package com.example.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * User entity class.
 *
 * @author sai praveen venturi
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
@Builder
public class User implements Serializable {

    /**
     * The unique identifier for employee.
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Name of the User.
     */
    @Column(name = "user_name", nullable = false, unique = true)
    private String name;

    /**
     * Password of the User.
     */
    @Column(name = "user_password", nullable = false)
    private String password;

    /**
     * Set of roles for the user.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles_table",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
