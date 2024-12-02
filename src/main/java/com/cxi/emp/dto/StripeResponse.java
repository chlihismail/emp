package com.cxi.emp.dto;

public record StripeResponse (
     String status,
     String message,
     String sessionId,
     String sessionUrl
)
{
  // Static builder class
    public static class Builder {
        private String status;
        private String message;
        private String sessionId;
        private String sessionUrl;

        // Setter-style methods for chaining
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder sessionUrl(String sessionUrl) {
            this.sessionUrl = sessionUrl;
            return this;
        }

        // Build method to create a StripeResponse instance
        public StripeResponse build() {
            return new StripeResponse(status, message, sessionId, sessionUrl);
        }
    }

    // Static factory method to initialize the builder
    public static Builder builder() {
        return new Builder();
    }
}
