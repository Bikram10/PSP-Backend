package com.bikram.appliedproject.service.helper;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.repositories.CategoryRepository;
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
import java.util.List;

public class CSVHelper {

    @Autowired
    private static CategoryRepository categoryRepository;


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
//            CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim();
            CSVParser csvParser = csvFormat.parse(bufferedReader);
            List<ProductDto> productDtos = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord: csvRecords){
                ProductDto productDto = new ProductDto();
                Type category = new Type(csvRecord.get("Type"));
                productDto.setType(category);
                productDto.setBrand(csvRecord.get("Brand"));
                productDto.setProduct_name(csvRecord.get("Name"));
                productDto.setCategory(csvRecord.get("Category"));
                productDto.setDescription(csvRecord.get("Description"));
                productDto.setSKU(csvRecord.get("SKU"));
                productDto.setPrice(Double.parseDouble(csvRecord.get("Price")));
                productDto.setQuantity(Integer.parseInt(csvRecord.get("Quantity")));

                productDtos.add(productDto);
            }


            return productDtos;
        }
        catch (Exception exception){
            throw new RuntimeException("Fail to parse CSV file:"+ exception.getMessage());
        }

    }
}
