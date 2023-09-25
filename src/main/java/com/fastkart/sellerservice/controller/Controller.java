package com.fastkart.sellerservice.controller;

import com.fastkart.sellerservice.config.CustomMultipartFile;
import com.fastkart.sellerservice.model.Product;
import com.fastkart.sellerservice.model.User;
import com.fastkart.sellerservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;

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
    public ResponseEntity<Object> sellerAddProduct(@RequestBody Product product){
        log.info("Request received to add seller product");


        return ResponseEntity.status(HttpStatus.OK).body(userService.addProduct(product));
    }

    @PostMapping("/addProducts/importFile/direct")
    public ResponseEntity<String> sellerAddProductImportFileDirect(@RequestParam("file") MultipartFile excelDataFile, @RequestParam("username") String username) throws IOException {
        log.info("Request received to add seller products from Excel File");

        /*LocalDateTime date = LocalDateTime.parse(new Date().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        System.out.println(date); //Mon Sep 25 13:52:26 IST 2023*/

        return ResponseEntity.status(HttpStatus.OK).body(userService.addProductsFromFile(excelDataFile, username));
    }

    //@PostMapping(path = ("/addProducts/importFile"), consumes = {"multipart/form-data"})
    /*@PostMapping("/addProducts/importFile")
    public ResponseEntity<String> sellerAddProductImportFile(@RequestParam("file") byte[] excelDataFileBytes) throws IOException {
        log.info("Request received to add seller products from Excel File");

        //FileItem fileItem = new DiskFileItem("fileData", "application/pdf",true, outputFile.getName(), 100000000, new java.io.File(System.getProperty("java.io.tmpdir")));
        MultipartFile multipartFile = new CustomMultipartFile();
        return ResponseEntity.status(HttpStatus.OK).body(userService.addProductsFromFile(multipartFile));
    }*/

}
