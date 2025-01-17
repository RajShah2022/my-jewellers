package com.my_jewellers.my_jewellers.description;

import com.my_jewellers.my_jewellers.category.Category;
import com.my_jewellers.my_jewellers.common.BaseEntity;
import com.my_jewellers.my_jewellers.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
