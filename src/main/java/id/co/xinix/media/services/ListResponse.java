package id.co.xinix.media.services;

import java.util.List;

public record ListResponse<D>(
        String message,
        List<D> data,
        Long count
) {}
