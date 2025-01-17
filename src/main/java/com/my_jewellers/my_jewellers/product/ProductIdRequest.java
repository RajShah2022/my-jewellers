package com.my_jewellers.my_jewellers.product;

import jakarta.validation.constraints.NotNull;

public record ProductIdRequest(
        @NotNull(message = "100")
        Integer id
) {
}