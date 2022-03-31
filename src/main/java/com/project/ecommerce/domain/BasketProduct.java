package com.project.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.dto.BasketProductDTO;
import lombok.*;

import javax.persistence.*;

@NamedNativeQuery(name = "BasketProduct.getAllByUsername",
                  query = """
                    SELECT bp.id as id, bp.product_id as productId, bp.amount as basketAmount, p.amount as productAmount, p.name as productName, p.details as productDetails, p.photo_url as productPhotoUrl, p.cost as cost, s.store_name as storeName, u.username as username FROM `e-commerce`.basket_product as bp
                    INNER JOIN `e-commerce`.product as p on bp.product_id = p.id
                    INNER JOIN `e-commerce`.app_user as u on bp.user_id = u.id
                    INNER JOIN `e-commerce`.store as s on s.id = p.store
                    WHERE username = :username""",
                    resultSetMapping = "Mapping.BasketProductDTO")
@SqlResultSetMapping(name = "Mapping.BasketProductDTO",
                    classes = @ConstructorResult(targetClass = BasketProductDTO.class,
                    columns = {
                            @ColumnResult(name="id", type = Long.class),
                            @ColumnResult(name="productId", type = Long.class),
                            @ColumnResult(name="basketAmount", type = Long.class),
                            @ColumnResult(name="productAmount", type = Long.class),
                            @ColumnResult(name="productName", type = String.class),
                            @ColumnResult(name="productDetails", type = String.class),
                            @ColumnResult(name="productPhotoUrl", type = String.class),
                            @ColumnResult(name="cost", type = Float.class),
                            @ColumnResult(name="storeName", type = String.class),
                            @ColumnResult(name="username", type = String.class),
                    }))

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private AppUser user;

}
