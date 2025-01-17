package com.my_jewellers.my_jewellers.description;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DescriptionResponse {
    private Integer id;
    private String descriptionName;

    private float quantity;

    private String units;
    //    private CategoryResponse category;
    private Integer categoryId;
}
