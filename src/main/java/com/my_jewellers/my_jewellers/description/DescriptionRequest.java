package com.my_jewellers.my_jewellers.description;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DescriptionRequest(

        Integer id,

        @NotNull
        @NotEmpty
        String descriptionName,

        @NotNull
        @NotEmpty
        float quantity,

        @NotNull
        @NotEmpty
        String units,

//        CategoryRequest category

        @NotNull
        @NotEmpty
        Integer categoryId

) {
}
