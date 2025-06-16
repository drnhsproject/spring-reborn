package id.co.xinix.media.services;

import java.util.List;

public record DataResponse<D>(
        List<D> result,
        Long count
) {}
