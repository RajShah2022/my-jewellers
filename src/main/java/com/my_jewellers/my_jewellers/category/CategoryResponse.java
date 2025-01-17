package com.my_jewellers.my_jewellers.category;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Integer id;
    private String categoryName;
    private float priceRate;
}
