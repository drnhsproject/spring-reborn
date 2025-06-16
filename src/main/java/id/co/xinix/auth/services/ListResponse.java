package id.co.xinix.auth.services;

import org.springframework.data.domain.Page;

public record ListResponse<D>(
        String message,
        DataResponse<D> data
) {
    public static <D> ListResponse<D> fromPage(String message, Page<D> page) {
        return new ListResponse<>(message, new DataResponse<>(page.getContent(), page.getTotalElements()));
    }
}
