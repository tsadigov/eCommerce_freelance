package com.project.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String firstName;

    @Column(unique = true, nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String country;

    @Column(unique = true, nullable = false)
    private String city;

    @Column(nullable = false)
    private String password;

    private String profilePictureUrl;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Role>roles;

    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "user")
    private Set<Orders> orders;

    @OneToMany(mappedBy = "user")
    private Set<BasketProduct> basketProducts;

    @OneToOne(mappedBy = "user")
    private SellerDetails sellerDetails;

}
