package com.my_jewellers.my_jewellers.product;

import com.my_jewellers.my_jewellers.common.BaseEntity;
import com.my_jewellers.my_jewellers.description.Description;
import com.my_jewellers.my_jewellers.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;
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

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<Image> imageList;

}
