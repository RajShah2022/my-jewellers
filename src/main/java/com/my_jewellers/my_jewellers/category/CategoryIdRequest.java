package com.my_jewellers.my_jewellers.category;

import jakarta.validation.constraints.NotNull;

public record CategoryIdRequest(
        @NotNull(message = "100")
        Integer id
) {
}
