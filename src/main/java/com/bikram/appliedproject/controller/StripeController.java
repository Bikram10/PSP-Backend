package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.stripe.CheckoutPayment;
import com.bikram.appliedproject.service.StripeService;
import com.google.gson.Gson;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/stripeApi")
public class StripeController {
    private static Gson gson = new Gson();

    @Autowired
    private StripeService stripeService;

    @PostMapping("/payment")
    public ResponseEntity<String> checkout(@RequestBody CheckoutPayment payment) throws StripeException {
        stripeService.init();

        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(payment.getCancelUrl())
                .setSuccessUrl(payment.getSuccessUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                                .builder().setName(payment.getName()).build()).build()).build()).build();
        Session session = Session.create(sessionCreateParams);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return ResponseEntity.ok().body(gson.toJson(responseData));
    }

}
