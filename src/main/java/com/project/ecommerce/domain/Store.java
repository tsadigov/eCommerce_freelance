package com.project.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String storeName;

    private String storeUrl;

    private String storeLogoUrl;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    @Fetch(value= FetchMode.SELECT)
    private Set<Product> products;

    @OneToOne(mappedBy = "store")
    private SellerDetails sellerDetails;

    public Store(Long id, String storeName, String storeUrl, String storeLogoUrl){
        this.id = id;
        this.storeName = storeName;
        this.storeUrl = storeUrl;
        this.storeLogoUrl = storeLogoUrl;
    }


}
