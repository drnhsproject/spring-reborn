package id.co.xinix.spring.services;

public record SingleResponse<D>(String message, D data) {}
