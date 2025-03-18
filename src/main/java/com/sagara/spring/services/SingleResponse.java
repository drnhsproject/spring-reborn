package com.sagara.spring.services;

import lombok.Getter;

@Getter
public record SingleResponse<D>(String message, D data) {}
