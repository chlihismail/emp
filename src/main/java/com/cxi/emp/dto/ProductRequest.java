package com.cxi.emp.dto;

public record ProductRequest(
    Long amount,
    Long quantity,
    String name,
    String currency
)
{
  public static class Builder {
        private Long amount;
        private Long quantity;
        private String name;
        private String currency;

        // Setter-style methods for chaining
        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        public Builder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        // Build method to create a ProductRequest instance
        public ProductRequest build() {
            return new ProductRequest(amount, quantity, name, currency);
        }
    }

    // Static factory method to initialize the builder
    public static Builder builder() {
        return new Builder();
    }
}
