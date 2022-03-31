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
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String addressLine1;

    private String addressLine2;

    private String country;

    private String city;

    private String state;

    private String postalCode;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;


}
