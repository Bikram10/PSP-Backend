package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.cart.Cart;
import com.bikram.appliedproject.domain.cart.CartItem;
import com.bikram.appliedproject.repositories.CartItemRepository;
import com.bikram.appliedproject.repositories.CartRepository;
import com.bikram.appliedproject.repositories.ProductRepository;
import com.bikram.appliedproject.repositories.UserRepository;
import com.bikram.appliedproject.service.CartService;
import com.bikram.appliedproject.service.dto.CartDto;
import com.bikram.appliedproject.service.dto.CartItemDto;
import com.bikram.appliedproject.service.exception.BadRequestException;
import com.bikram.appliedproject.service.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductRepository productRepository;


    @Override
    @Transactional
    public CartDto saveCart(CartItemDto cartItemDto) throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        Cart cart =cartRepository.findByUser(user.getUser_id()).orElseGet(() -> createCart(user));


        user.setCart(cart);

        CartItem cartItem = cartMapper.DtoToCartItem(cartItemDto);

        Boolean found = false;
        Set<CartItem> foundItems = new HashSet<>();

        Set<CartItem> cartItemSet = new HashSet<>();
        if(cart.getCartItems() == null){
            found = false;
        }
        else{
            foundItems = cart.getCartItems();
        }


        for(CartItem item: foundItems){
            if(item.getProduct().getProduct_id() == cartItem.getProduct().getProduct_id()){
                found = true;
                item.setQuantity(cartItem.getQuantity());
            }

            cartItemSet.add(item);
        }

        if(!found)
            cartItemSet.add(cartItem);

        cart.setCartItems(cartItemSet);

        cart = cartRepository.save(cart);



        return cartMapper.cartToDto(cart);
    }

    public Cart createCart(User user){
        Cart cart = new Cart();

        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public CartDto getCart(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());

        Optional<Cart> optionalCart = cartRepository.findByUser(user.getUser_id());
        Cart cart = optionalCart.isPresent() ? optionalCart.get() : null;

        CartDto cartDto = cartMapper.cartToDto(cart);


        return cartDto;
    }

    @Override
    public List<CartDto> getCartItems() {
        return null;
    }

    @Override
    public Integer getCartTotal() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        if(user !=null) {
            Optional<Cart> cartOptional = cartRepository.findByUser(user.getUser_id());
            Cart cart = cartOptional.isPresent() ? cartOptional.get() : null;
            Set<CartItem> cartItems = cart.getCartItems();
            return cartItems.size();
        }

        return 0;
    }

    @Override
    @Transactional
    public void deleteCart(Long id) throws Exception {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new BadRequestException("No such Cart Item!"));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());

        Optional<Cart> optionalCart = cartRepository.findByUser(user.getUser_id());
        Cart cart = optionalCart.isPresent()? optionalCart.get() : null;
        Set<CartItem> cartItems = cart.getCartItems();
        cartItem.setProduct(null);
        cartItems.remove(cartItem);
        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        cartItemRepository.delete(cartItem);
    }

    public Cart createCartOfUser(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public CartDto updateCartItemQuantity(CartItemDto cartItemDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());

        Optional<Cart> cartOptional = cartRepository.findByUser(user.getUser_id());
        Cart cart = cartOptional.isPresent() ? cartOptional.get() : null;


        cart.setCartItems(cart.getCartItems().stream().map(cartItem -> {
            if(cartItem.getId() == cartItemDto.getId()){
                cartItem.setQuantity(cartItemDto.getQuantity());
            }
            return cartItem;
        }).collect(Collectors.toSet()));

        cart = cartRepository.save(cart);

        return cartMapper.cartToDto(cart);
    }

    @Override
    public void deleteCartItems(Long UserId) {
        cartRepository.deleteAll();
    }
}
