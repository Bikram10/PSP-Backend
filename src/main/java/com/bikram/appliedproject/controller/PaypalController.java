package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/paypalApi")
@CrossOrigin("*")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam("sum") Double sum){
        return paypalService.createPayment(sum);
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request){
        return paypalService.completePayment(request);
    }
}
