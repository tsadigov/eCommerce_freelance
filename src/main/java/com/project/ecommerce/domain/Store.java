package com.project.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String storeUrl;

    private String storeLogoUrl;

    @OneToMany(mappedBy = "store")
    private Set<Product> products;

    @OneToOne(mappedBy = "store")
    private SellerDetails sellerDetails;

}
