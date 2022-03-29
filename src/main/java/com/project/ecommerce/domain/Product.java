package com.project.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Float cost;

    @Column(nullable = false)
    private String details;

    private String photoUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="subcategory_id")
    private Subcategory subcategory;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store")
    private Store store;

    @OneToMany(mappedBy = "product")
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "product")
    private Set<Orders> orders;

    @OneToMany(mappedBy = "product")
    private Set<BasketProduct> basketProducts;

//    @OneToMany(mappedBy = "product")
//    private Set<ProductPhoto> photos;

}
