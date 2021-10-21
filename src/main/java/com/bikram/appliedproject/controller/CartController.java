package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.repositories.UserRepository;
import com.bikram.appliedproject.service.CartService;
import com.bikram.appliedproject.service.dto.CartDto;
import com.bikram.appliedproject.service.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartApi")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addData")
    public ResponseEntity<CartDto> saveCart(@RequestBody CartItemDto cartItemDto) throws Exception {
        return ResponseEntity.ok().body(cartService.saveCart(cartItemDto));
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotal(){
        return ResponseEntity.ok().body(cartService.getCartTotal());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/listCart")
    public ResponseEntity<CartDto> getAll(){
        return ResponseEntity.ok().body(cartService.getCart());
    }

    @PostMapping("/update")
    public ResponseEntity<CartDto> updateQuantity(@RequestBody CartItemDto cartItemDto){
        return ResponseEntity.ok().body(cartService.updateCartItemQuantity(cartItemDto));
    }

    @PostMapping("/delete/{cartItemId}")
    public void deleteCartItem(@PathVariable String cartItemId) throws Exception {
        cartService.deleteCart(Long.parseLong(cartItemId));
    }
}
