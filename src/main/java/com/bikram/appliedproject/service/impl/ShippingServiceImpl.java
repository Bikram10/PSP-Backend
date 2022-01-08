package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.order.Shipping;
import com.bikram.appliedproject.repositories.OrderRepository;
import com.bikram.appliedproject.repositories.ShippingRepository;
import com.bikram.appliedproject.repositories.UserRepository;
import com.bikram.appliedproject.service.ShippingService;
import com.bikram.appliedproject.service.dto.ShippingDto;
import com.bikram.appliedproject.service.mapper.ShippingMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public JsonNode calculatePostage() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        Optional<Shipping> optionalShipping = shippingRepository.findByUser(user.getUser_id());
        Shipping shipping = optionalShipping.isPresent() ? optionalShipping.get() : null;

        String apiKey = "a8d2c7d0-e4f1-4c88-b477-6766df84e79b\n";

// Set the URL for the Domestic Parcel Size service
        String urlPrefix = "digitalapi.auspost.com.au";
        String postageTypesURL = "https://" + urlPrefix + "/postage/parcel/domestic/calculate.json?";

// Lookup domestic parcel types (different kinds of standard boxes etc)
        DefaultHttpClient httpclient = new DefaultHttpClient();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("from_postcode", "2000"));
        params.add(new BasicNameValuePair("to_postcode", shipping.getZip().toString()));
        params.add(new BasicNameValuePair("length", "22"));
        params.add(new BasicNameValuePair("width", "16"));
        params.add(new BasicNameValuePair("height", "7.7"));
        params.add(new BasicNameValuePair("weight", "1.5"));
        params.add(new BasicNameValuePair("service_code", "AUS_PARCEL_REGULAR"));
        String query = URLEncodedUtils.format(params, "UTF-8");

        HttpGet httpGet = new HttpGet(postageTypesURL + query);
        httpGet.setHeader("AUTH-KEY", apiKey);
        HttpResponse response = httpclient.execute(httpGet);

// Check the response: if the body is empty then an error occurred
        if(response.getStatusLine().getStatusCode() != 200){
            throw new Exception("Error: '" + response.getStatusLine().getReasonPhrase() + "' - Code: " + response.getStatusLine().getStatusCode());
        }

// All good, lets parse the response into a JSON object
        try {
            byte[] responseBody = EntityUtils.toByteArray(response.getEntity());
            JsonNode rawBody = new ObjectMapper().readValue(responseBody, JsonNode.class);
            return rawBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
