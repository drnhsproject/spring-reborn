package com.sagara.spring.services;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListResponse<D> {

    private DataWrapper<D> data;

    public ListResponse(String message, List<D> result, Long count) {
        this.data = new DataWrapper<>(message, result, count);
    }

    @Setter
    @Getter
    public static class DataWrapper<D> {

        private String message;
        private List<D> result;
        private Long count;

        public DataWrapper(String message, List<D> result, Long count) {
            this.message = message;
            this.result = result;
            this.count = count;
        }

    }
}
