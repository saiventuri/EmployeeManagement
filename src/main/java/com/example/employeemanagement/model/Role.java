package com.example.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Role entity class.
 *
 * @author sai praveen venturi
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_table")
@Builder
public class Role implements Serializable {

    /**
     * The unique identifier for role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;

    /**
     * Name of the Role.
     */
    @Column(name = "role_name", nullable = false, unique = true)
    private String name;
}
