package com.my_jewellers.my_jewellers.common;

import java.util.Objects;

public record PageRequest(
        Integer page,
        Integer size
) {
    public PageRequest(Integer page, Integer size) {
        this.page = Objects.requireNonNullElse(page, 0);
        this.size = Objects.requireNonNullElse(size, 10);
    }
}
