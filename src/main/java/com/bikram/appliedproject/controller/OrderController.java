package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.order.Order;
import com.bikram.appliedproject.service.OrderService;
import com.bikram.appliedproject.service.dto.OrderDto;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.exception.BadOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderApi")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    private ResponseEntity<OrderDto> makeOrder(){
        try {
            return ResponseEntity.ok().body(orderService.placeOrder());
        }
        catch (BadOrderException e){
            return ResponseEntity.ok().body(new OrderDto(e.getMessage()));
        }
    }

    @GetMapping("/order")
    private ResponseEntity<Order> getOrders(){
        return ResponseEntity.ok().body(orderService.getOrder());
    }

    @PostMapping("/productRating")
    private ResponseEntity<ProductDto> setRating(@RequestPart("orderItem") ProductDto orderItemDto, @RequestPart("rate") Integer rate){
        return ResponseEntity.ok().body(orderService.saveRating(orderItemDto, rate));
    }

    @PostMapping("/updateStatus")
    private void updateStatus(@RequestParam("id") Long orderId, @RequestParam("status") String selectedVal){
        orderService.updateStatus(selectedVal, orderId);
    }

    @GetMapping("/orderList")
    private ResponseEntity<List<Order>> getAllOrder(){
        return ResponseEntity.ok().body(orderService.getOrderList());
    }
}
