package com.cxi.emp.dto;

public record ProductRequest(
    Long amount,
    Long quantity,
    String name,
    String currency
)
{}
