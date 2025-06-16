package id.co.xinix.auth.services;

import java.util.List;

public record DataResponse<D>(
        List<D> result,
        Long count
) {}
