package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.service.HomePageService;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/homeApi")
@CrossOrigin("*")
public class HomeController {

    @Autowired
    private HomePageService homePageService;


    @GetMapping("/latest")
    public ResponseEntity<Page<Product>> getLatestItem(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) throws Exception{
        return ResponseEntity.ok().body(homePageService.getNewItem(page, size));
    }

    @GetMapping("/product-info/{productId}")
    public ResponseEntity<ProductDto> getProductInfo(@PathVariable String productId){
        return ResponseEntity.ok().body(homePageService.getProductInfo(Long.parseLong(productId)));
    }

    @GetMapping("/demo")
    private ResponseEntity<JsonNode> getRequest()
    {
        //Java dependencies are Apache HTTP Client 4, Commons Logging 1.1 and Jackson 1.9 jars

// Set your API key: remember to change this to your live API key in production
        String apiKey = "a8d2c7d0-e4f1-4c88-b477-6766df84e79b";

// Set the URL for the Domestic Parcel Size service
        String urlPrefix = "digitalapi.auspost.com.au";
        String parcelTypesURL = "https://" + urlPrefix + "/postage/parcel/domestic/size.json";

// Lookup domestic parcel types (different kinds of standard boxes etc)
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(parcelTypesURL);
        httpGet.setHeader("AUTH-KEY", apiKey);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

// Check the response: if the body is empty then an error occurred
        if(response.getStatusLine().getStatusCode() != 200){
            try {
                throw new Exception("Error: '" + response.getStatusLine().getReasonPhrase() + "' - Code: " + response.getStatusLine().getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

// All good, lets parse the response into a JSON object
        try {
            byte[] responseBody = EntityUtils.toByteArray(response.getEntity());
            JsonNode rawBody = new ObjectMapper().readValue(responseBody, JsonNode.class);
            return ResponseEntity.ok().body(rawBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/viewItem")
    public ResponseEntity<Page<Product>> getMostViewItem(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size){
        return ResponseEntity.ok().body(homePageService.getViewedItem(page, size));
    }

    @GetMapping("/recommendedItems")
    public ResponseEntity<Page<Product>> getRecommendedItems(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size, @RequestParam String type){
        return ResponseEntity.ok().body(homePageService.getRecommendedItems(page, size, type));
    }
}
