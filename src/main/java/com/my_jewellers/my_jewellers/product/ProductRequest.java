package com.my_jewellers.my_jewellers.product;

import com.my_jewellers.my_jewellers.description.DescriptionRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductRequest(

        Integer id,

        @NotNull
        @NotEmpty
        String productName,

        List<DescriptionRequest> descriptionList

) {
}
