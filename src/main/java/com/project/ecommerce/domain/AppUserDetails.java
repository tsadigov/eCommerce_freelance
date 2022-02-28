package com.project.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    private String postalCode;

    private String address;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AppUser userFk;

    @ManyToOne
    private Store store;

}
