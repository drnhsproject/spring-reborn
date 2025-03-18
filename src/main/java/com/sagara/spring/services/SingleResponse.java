package com.sagara.spring.services;

public record SingleResponse<D>(String message, D data) {}
