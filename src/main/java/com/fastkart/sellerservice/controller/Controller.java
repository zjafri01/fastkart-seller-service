package com.fastkart.sellerservice.controller;

import com.fastkart.sellerservice.model.Product;
import com.fastkart.sellerservice.model.User;
import com.fastkart.sellerservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/seller")
@CrossOrigin
@Slf4j
public class Controller {

    private LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        log.info("Request received");

        return ResponseEntity.status(HttpStatus.OK).body("Hello Master Service");

    }

    @PostMapping("/viewProducts")
    public ResponseEntity<Object> sellerViewProducts(@RequestBody User user){
        log.info("Request received to view seller products");

        try {
            responseMap.clear();
            return ResponseEntity.status(HttpStatus.OK).body(userService.viewProducts(user));
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorMessage", e.getMessage());
            responseMap.put("errorMessage",e.getMessage());
            responseMap.put("statusCode",HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseMap);
        }
    }

    @PostMapping("/addProducts")
    public ResponseEntity<String> sellerAddProduct(@RequestBody Product product){
        log.info("Request received to add seller products");


        return ResponseEntity.status(HttpStatus.OK).body(userService.addProduct(product));
    }

    @PostMapping("/addProducts/importFile")
    public ResponseEntity<String> sellerAddProductImportFile(@RequestParam("file") MultipartFile excelDataFile) throws IOException {
        log.info("Request received to add seller products from Excel File");

        return ResponseEntity.status(HttpStatus.OK).body(userService.addProductsFromFile(excelDataFile));
    }

}
