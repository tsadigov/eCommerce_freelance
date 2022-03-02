package com.project.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Float balance;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private AppUser user;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "store", referencedColumnName = "id")
    private Store store;

}
