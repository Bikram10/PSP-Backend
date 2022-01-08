package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.service.HomePageService;
import com.bikram.appliedproject.service.ProductService;
import com.bikram.appliedproject.service.dto.ProductDto;
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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/homeApi")
@CrossOrigin("*")
public class HomeController {

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private ProductService productService;


    @GetMapping("/latest")
    public ResponseEntity<Page<Product>> getLatestItem(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) throws Exception{
        return ResponseEntity.ok().body(homePageService.getNewItem(page, size));
    }

    @GetMapping("/product-info/{productId}")
    public ResponseEntity<ProductDto> getProductInfo(@PathVariable String productId){
        return ResponseEntity.ok().body(homePageService.getProductInfo(Long.parseLong(productId)));
    }

    @GetMapping("/demo")
    private ResponseEntity<JsonNode> getRequest() throws Exception {
        //Java dependencies are Apache HTTP Client 4, Commons Logging 1.1 and Jackson 1.9 jars

// Set your API key: remember to change this to your live API key in production
        String apiKey = "your_api_key";

// Set the URL for the Domestic Parcel Size service
        String urlPrefix = "digitalapi.auspost.com.au";
        String postageTypesURL = "https://" + urlPrefix + "/postage/parcel/domestic/service.json?";

// Lookup domestic parcel types (different kinds of standard boxes etc)
        DefaultHttpClient httpclient = new DefaultHttpClient();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("from_postcode", "2000"));
        params.add(new BasicNameValuePair("to_postcode", "3000"));
        params.add(new BasicNameValuePair("length", "22"));
        params.add(new BasicNameValuePair("width", "16"));
        params.add(new BasicNameValuePair("height", "7.7"));
        params.add(new BasicNameValuePair("weight", "1.5"));
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

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok().body(productService.getAll());
    }
}
