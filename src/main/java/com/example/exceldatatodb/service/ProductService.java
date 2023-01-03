package com.example.exceldatatodb.service;

import com.example.exceldatatodb.entity.Product;
import com.example.exceldatatodb.repo.ProductRepo;
import com.example.exceldatatodb.utility.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public void save(MultipartFile file, String sprintValue) {
        try {
            List<Product> products = new ArrayList<>();
            products = ExcelUtility
                    .convertExcelToList(file.getInputStream());
            if(sprintValue.length() > 0){
                for(Product prod : products) {
                    prod.setSprintValue(sprintValue);
                }
            }

            this.productRepo.saveAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        return this.productRepo.findAll();
    }


    public List<Product> getBySprintValue(String sprintValue) {
        return this.productRepo.findAll();
    }
}
