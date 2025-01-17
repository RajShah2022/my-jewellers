package com.my_jewellers.my_jewellers.product;

import com.my_jewellers.my_jewellers.common.BaseEntity;
import com.my_jewellers.my_jewellers.description.Description;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity {

    private String productName;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<Description> descriptionList;

//    public float getPrice() {
//        float finalPrice = 0f;
//        for (Description desc : descriptionList) {
//            finalPrice += desc.getQuantity() * desc.getCategory().getPriceRate();
//        }
//        return finalPrice;
//    }

}
