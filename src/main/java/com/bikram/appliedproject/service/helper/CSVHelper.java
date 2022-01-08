package com.bikram.appliedproject.service.helper;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.domain.product.ImageUrls;
import com.bikram.appliedproject.domain.product.StockStatus;
import com.bikram.appliedproject.repositories.CategoryRepository;
import com.bikram.appliedproject.service.CloudinaryService;
import com.bikram.appliedproject.service.dto.ProductDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVHelper {

    @Autowired
    private static CategoryRepository categoryRepository;

    @Autowired
    private static CloudinaryService cloudinaryService;


    public static String type="text/csv";

    public static boolean hasCSVFormat(MultipartFile file){
        if(!type.equals(file.getContentType())){
            return false;
        }
        return true;
    }

    public static List<ProductDto> csvToProduct(InputStream ins){
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim();
            CSVParser csvParser = csvFormat.parse(bufferedReader);
            List<ProductDto> productDtos = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord: csvRecords){
                    ProductDto productDto = new ProductDto();
                    Type category = new Type(csvRecord.get("Type"));

                    productDto.setType(category);
                    StockStatus stockStatus = StockStatus.valueOf(csvRecord.get("Stock Status"));
                    productDto.setBrand(csvRecord.get("Brand"));
                    productDto.setProduct_name(csvRecord.get("Name"));
                    productDto.setCategory(csvRecord.get("Category"));
                    productDto.setShort_description("Short Description");
                    productDto.setDescription(csvRecord.get("Description"));
                    productDto.setSKU(csvRecord.get("SKU"));
                    productDto.setPrice(Double.parseDouble(csvRecord.get("Price")));
                    productDto.setStockStatus(stockStatus);
                    productDto.setQuantity(Integer.parseInt(csvRecord.get("Quantity")));
                    String[] urls = csvRecord.get("url").split(" ");
                    Set<ImageUrls> imageUrlsSet = new HashSet<>();
                    int i=0;
                    ImageUrls imageUrls = null;
                for(String url: urls){
                    imageUrls = new ImageUrls();
                    imageUrls.setUrl(url);
                    imageUrlsSet.add(imageUrls);
                }
                productDto.setProduct_img_url(imageUrlsSet);
                    productDtos.add(productDto);
            }


            return productDtos;
        }
        catch (Exception exception){
            throw new RuntimeException("Fail to parse CSV file:"+ exception.getMessage());
        }

    }
}
