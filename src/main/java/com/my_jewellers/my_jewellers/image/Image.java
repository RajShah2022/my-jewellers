package com.my_jewellers.my_jewellers.image;

import com.my_jewellers.my_jewellers.common.BaseEntity;
import com.my_jewellers.my_jewellers.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
@EntityListeners(AuditingEntityListener.class)
public class Image extends BaseEntity {

    private String imageName;

    private String imageType;

    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    public Product getProduct() {
        return product;
    }
}
