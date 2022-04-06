package com.project.ecommerce.domain;

import com.project.ecommerce.dto.BasketProductDTO;
import com.project.ecommerce.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NamedNativeQuery(name = "Orders.getAllByUsername",
        query = """
                    SELECT o.id as id, o.product_id as productId, o.amount as orderAmount, p.amount as productAmount, p.name as productName, p.details as productDetails, p.photo_url as productPhotoUrl, p.cost as cost, s.store_name as storeName, u.username as username, o.track_number as trackNumber, o.order_date as orderDate, o.status as orderStatus FROM `e-commerce`.orders as o
                    INNER JOIN `e-commerce`.product as p on o.product_id = p.id
                    INNER JOIN `e-commerce`.app_user as u on o.user_id = u.id
                    INNER JOIN `e-commerce`.store as s on s.id = p.store
                    WHERE username = :username""",
        resultSetMapping = "Mapping.OrderDTO")
@SqlResultSetMapping(name = "Mapping.OrderDTO",
        classes = @ConstructorResult(targetClass = OrderDTO.class,
                columns = {
                        @ColumnResult(name="id", type = Long.class),
                        @ColumnResult(name="productId", type = Long.class),
                        @ColumnResult(name="orderAmount", type = Long.class),
                        @ColumnResult(name="productName", type = String.class),
                        @ColumnResult(name="productDetails", type = String.class),
                        @ColumnResult(name="productPhotoUrl", type = String.class),
                        @ColumnResult(name="cost", type = Float.class),
                        @ColumnResult(name="storeName", type = String.class),
                        @ColumnResult(name="username", type = String.class),
                        @ColumnResult(name="trackNumber", type = String.class),
                        @ColumnResult(name="orderDate", type = LocalDate.class),
                        @ColumnResult(name="orderStatus", type = String.class),
                }))

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDate orderDate;

    private String trackNumber;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private AppUser user;

}
