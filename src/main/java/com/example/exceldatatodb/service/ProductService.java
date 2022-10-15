package com.example.exceldatatodb.service;

import com.example.exceldatatodb.entity.Product;
import com.example.exceldatatodb.repo.ProductRepo;
import com.example.exceldatatodb.utility.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public void save(MultipartFile file){
        try {
            List<com.example.exceldatatodb.entity.Product> products = ExcelUtility.convertExcelToList(file.getInputStream());
            this.productRepo.saveAll(products);
        } catch (IOException e) {
           e.printStackTrace();
        }

    }
    public List<Product> getAllProducts(){
       return this.productRepo.findAll();

    }
}
