package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.cart.Cart;
import com.bikram.appliedproject.domain.cart.CartItem;
import com.bikram.appliedproject.domain.order.Order;
import com.bikram.appliedproject.domain.order.OrderItem;
import com.bikram.appliedproject.domain.order.OrderStatus;
import com.bikram.appliedproject.domain.order.Shipping;
import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.repositories.*;
import com.bikram.appliedproject.service.CartService;
import com.bikram.appliedproject.service.OrderService;
import com.bikram.appliedproject.service.dto.OrderDto;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.mapper.OrderMapper;
import com.bikram.appliedproject.service.mapper.ProductMapper;
import com.bikram.appliedproject.service.mapper.ShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public OrderDto placeOrder(){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername());

            Optional<Cart> optionalCart = cartRepository.findByUser(user.getUser_id());
            Cart cart = optionalCart.isPresent() ? optionalCart.get() : null;

        Set<CartItem> cartItems = cart.getCartItems();

        if(cartItems.size() == 0)
            return null;

        System.out.println(cartItems.size());
        Order order = new Order();

            order.setUser(user);

            Double grandTotal = 0.0;
            Double subtotal = 0.0;

            Set<OrderItem> orderItemList = new HashSet<>();
            Set<CartItem> items = new HashSet<>();
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());

                orderItem.setTotal(cartItem.getProduct().getPrice() * orderItem.getQuantity());
                subtotal += orderItem.getTotal();

                cartItem.setProduct(null);
                orderItemRepository.save(orderItem);

                orderItemList.add(orderItem);
                cartItems.remove(cartItem);
            }

            cartItems.forEach(cartItem -> cartItemRepository.delete(cartItem));
            cartRepository.save(cart);

            grandTotal = subtotal + 0;

            order.setOrderItems(orderItemList);
            order.setSubTotal(subtotal);
            order.setGrandTotal(grandTotal);
            order.setOrderStatus(OrderStatus.pending);
            cart.setCartItems(new HashSet<>());
            order = orderRepository.save(order);

            Optional<Shipping> shippingDto = shippingRepository.findByUser(user.getUser_id());
            Shipping shipping = shippingDto.isPresent() ? shippingDto.get() : null;

            order.setShipping(shipping);


            order = orderRepository.save(order);

            items.forEach(cartItem -> cartItemRepository.delete(cartItem));

            return orderMapper.orderToDto(order);
    }

    @Override
    public Order getOrder() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());

        Optional<Order> optionalOrder = orderRepository.findByUser(user.getUser_id());
        Order order = optionalOrder.isPresent()? optionalOrder.get() : null;

        return order;
    }

    @Override
    public ProductDto saveRating(ProductDto orderItemDto, Integer rate) {
        System.out.println(orderItemDto);

        Product product = productMapper.DtoToProduct(orderItemDto);
        Optional<Product> optionalProduct = productRepository.findById(product.getProduct_id());
        Product product1 = optionalProduct.isPresent() ? optionalProduct.get() : null;

        product1.setRate(rate);

        product1 = productRepository.save(product1);

        return productMapper.productToDto(product1);
    }

    @Override
    public List<Order> getOrderList() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public void updateStatus(String status, Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.isPresent() ? optionalOrder.get() : null;

        OrderStatus orderStatus = OrderStatus.valueOf(status);
        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

    }
}
