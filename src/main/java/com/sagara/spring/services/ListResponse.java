package com.sagara.spring.services;

import java.util.List;

public record ListResponse<D>(
        String message, List<D> data, Long count
) {}
