package com.project.ecommerce.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private AppUser user;

    @OneToOne
    @JoinColumn(name = "store", referencedColumnName = "id")
    private Store store;

}
