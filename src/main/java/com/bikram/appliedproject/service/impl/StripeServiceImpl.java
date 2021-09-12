package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.service.StripeService;
import com.stripe.Stripe;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

    @Override
    public void init() {

        Stripe.apiKey = "sk_test_51JIXQTJwuSCDw6jhB6IPufKCGA27A27O79lhPjocEWojP549Eh0wvOQPe2eFCixNQTGqpcN6Cb60ohfS7TmaJLC700HiCk5jtK";
    }
}
