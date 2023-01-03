package com.example.exceldatatodb.restcontrollers;

import com.example.exceldatatodb.entity.Product;
import com.example.exceldatatodb.service.ProductService;
import com.example.exceldatatodb.utility.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ProductRestController {
    @Autowired
    private ProductService prodService;

    @PostMapping(value = "/product/upload", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Error", "Please select a excel file"));
        if (ExcelUtility.checkExcelFormat(file)) {
            this.prodService.save(file, "");
            return ResponseEntity.ok(Map.of("message", "Excel file uploaded successfully and data is saved in DB"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error", "Please upload a excel file with .xlsx format"));
    }


    @GetMapping(value = "/product", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Product> getAllProducts() {
        return this.prodService.getAllProducts();
    }


    @PostMapping(value = "/product/upload/{sprintValue}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fileUploadWithSprintValue(@PathVariable String sprintValue, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || sprintValue.isBlank())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Error", "Please select a excel file and try providing sprint value"));
        if (ExcelUtility.checkExcelFormat(file)) {
            this.prodService.save(file, sprintValue);
            return ResponseEntity.ok(Map.of("message", "Excel file uploaded successfully and data is saved in DB"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error", "Please upload a excel file with .xlsx format"));
    }


    @GetMapping(value = "/sprint", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Product> getBySprintNumber(@RequestParam("sprintValue") String sprintValue) {
        return this.prodService.getBySprintValue(sprintValue);
    }


}
