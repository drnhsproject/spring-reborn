package id.co.xinix.spring.services;

import java.util.List;

public record DataResponse<D>(
        List<D> result,
        Long count
) {}
