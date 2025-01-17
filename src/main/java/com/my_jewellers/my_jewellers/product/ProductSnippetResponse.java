package com.my_jewellers.my_jewellers.product;

import com.my_jewellers.my_jewellers.description.DescriptionResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSnippetResponse {
    private Integer id;
    private String productName;
    private String image;
}
