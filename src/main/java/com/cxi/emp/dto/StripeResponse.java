package com.cxi.emp.dto;

public record StripeResponse (
     String status,
     String message,
     String sessionId,
     String sessionUrl
){}
