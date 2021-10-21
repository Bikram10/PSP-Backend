package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.order.Shipping;
import com.bikram.appliedproject.repositories.OrderRepository;
import com.bikram.appliedproject.repositories.ShippingRepository;
import com.bikram.appliedproject.repositories.UserRepository;
import com.bikram.appliedproject.service.ShippingService;
import com.bikram.appliedproject.service.dto.ShippingDto;
import com.bikram.appliedproject.service.mapper.ShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ShippingDto save(ShippingDto shippingDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        Optional<Shipping> optionalShipping = shippingRepository.findByUser(user.getUser_id());
        Shipping shipping = optionalShipping.isPresent() ? optionalShipping.get() : null;
        System.out.println(shipping);
        if(shipping == null){
            Set<Shipping> shippingHashSet = new HashSet<>();
            Shipping shipping1 = shippingMapper.DtoToShipping(shippingDto);
            shippingHashSet.add(shipping1);

            user.setShipping(shippingHashSet);

            shipping1.setUser(user);
            shipping1 = shippingRepository.save(shipping1);

            return shippingMapper.shippingToDto(shipping1);
        }
        else{
            Set<Shipping> shippingHashSet1 = new HashSet<>();

            Shipping NewShipping = shippingMapper.DtoToShipping(shippingDto);
                shipping.setUser(user);
                shipping.setAddress(NewShipping.getAddress());
                shipping.setCity(NewShipping.getCity());
                shipping.setEmail(NewShipping.getEmail());
                shipping.setState(NewShipping.getState());
                shipping.setZip(NewShipping.getZip());
                shippingHashSet1.add(shipping);

                user.setShipping(shippingHashSet1);

                shipping = shippingRepository.save(shipping);

            return shippingMapper.shippingToDto(shipping);
        }
    }
}
