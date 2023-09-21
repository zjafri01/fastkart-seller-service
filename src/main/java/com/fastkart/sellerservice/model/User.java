package com.fastkart.sellerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String role;

    @OneToMany
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Product> product;

}