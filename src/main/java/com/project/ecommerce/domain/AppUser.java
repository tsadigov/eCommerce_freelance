package com.project.ecommerce.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String password;

    private String profilePictureUrl;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Role>roles;

    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "user")
    private Set<Orders> orders;

    @OneToMany(mappedBy = "user")
    private Set<BasketProduct> basketProducts;

    @OneToMany(mappedBy = "user")
    private Set<CustomerDetails> customerDetails;

    @OneToOne(mappedBy = "user")
    private SellerDetails sellerDetails;

}
