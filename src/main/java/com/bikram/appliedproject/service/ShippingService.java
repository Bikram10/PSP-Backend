package com.bikram.appliedproject.service;

import com.bikram.appliedproject.service.dto.ShippingDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface ShippingService {

    ShippingDto save(ShippingDto shippingDto);

    JsonNode calculatePostage() throws Exception;
}
