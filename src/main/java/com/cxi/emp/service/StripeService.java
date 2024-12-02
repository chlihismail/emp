package com.cxi.emp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.cxi.emp.dto.ProductRequest;
import com.cxi.emp.dto.StripeResponse;

@Service
public class StripeService{

  @Value("${stripe.secret.key}")
  private String secretKey ;

  public StripeResponse checkoutProducts(ProductRequest productRequest){
    Stripe.apiKey=secretKey ;
    SessionCreateParams.LineItem.PriceData.ProductData.Builder productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
    .setName(productRequest.name());

    SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
    .setCurrency(productRequest.currency() == null ? "USD" : productRequest.currency())
    .setUnitAmount(productRequest.amount())
    .setProductData(productData.build())
    .build();

    SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
    .setQuantity(productRequest.quantity())
    .setPriceData(priceData)
    .build();

    final SessionCreateParams params = SessionCreateParams.builder()
    .setMode(SessionCreateParams.Mode.PAYMENT)
    .setSuccessUrl("http://localhost:8080/success")
    .setCancelUrl("http://localhost:8080/cancel")
    .addLineItem(lineItem)
    .build();

    Session session = null ;
    try {
      session = Session.create(params) ;
    } catch (StripeException e) {
      System.out.println(e.getMessage());
    }
    return StripeResponse.builder()
    .status("SUCCESS")
    .message("Payment session created ")
    .sessionId(session.getId())
    .sessionUrl(session.getUrl())
    .build();
  }

}
