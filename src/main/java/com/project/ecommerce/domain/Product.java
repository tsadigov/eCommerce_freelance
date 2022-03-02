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

    @ManyToOne
    @JoinColumn(name="subcategory_id", nullable=false)
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "store", referencedColumnName = "id")
    private Store store;

    @OneToMany(mappedBy = "product")
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "product")
    private Set<Orders> orders;

    @OneToMany(mappedBy = "product")
    private Set<BasketProduct> basketProducts;

}
