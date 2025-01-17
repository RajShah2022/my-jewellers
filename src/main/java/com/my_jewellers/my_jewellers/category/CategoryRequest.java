package com.my_jewellers.my_jewellers.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(

        Integer id,

        @NotNull
        @NotEmpty
        String categoryName,

        @NotNull
//        @NotEmpty
        float priceRate
) {
}
