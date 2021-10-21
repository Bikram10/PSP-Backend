package com.bikram.appliedproject.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PaypalService {

    Map<String, Object> createPayment(Double sum);

    Map<String, Object> completePayment(HttpServletRequest req);
}
