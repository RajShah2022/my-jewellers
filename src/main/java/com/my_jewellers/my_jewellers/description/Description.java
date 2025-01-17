package com.my_jewellers.my_jewellers.description;

import com.my_jewellers.my_jewellers.common.BaseEntity;
import com.my_jewellers.my_jewellers.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Description extends BaseEntity {

    private String descriptionName;

    private float quantity;

    private String units;

//    @OneToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    private Category category;

    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
