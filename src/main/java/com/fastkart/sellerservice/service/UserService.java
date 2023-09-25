package com.fastkart.sellerservice.service;

import com.fastkart.sellerservice.model.Product;
import com.fastkart.sellerservice.model.User;
import com.fastkart.sellerservice.repository.ProductRepository;
import com.fastkart.sellerservice.repository.UserDetailsRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> viewProducts(User user) throws Exception {
        List<Product> userProducts = null;
        User userDetails = null;

        try{
            userDetails = userDetailsRepository.findByUsername(user.getUsername());
            userProducts = productRepository.findProductByUserId(userDetails.getId());
            if(userProducts.isEmpty()){
                throw new Exception("No products listed by you! List to sell fast!");
            }
        }
        catch (NullPointerException e){
            throw new Exception("Null");
        }
        return userProducts;
    }

    public Product addProduct(Product product){
        User userDetails = null;

        try{
            userDetails = userDetailsRepository.findByUsername(product.getUsername());
            product.setUser(userDetails);
            product.setCurrent_bid_amt(product.getMin_bid_amt_seller());
            product.setListed_datetime(new Date().toString());
            product.setBid(List.of());
        }
        catch (NullPointerException e){
            return null;
        }
        return productRepository.save(product);
    }

    public String addProductsFromFile(MultipartFile excelDataFile, String username) throws IOException {

        User userDetails = null;
        List<Product> productList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(excelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            Product product = new Product();

            XSSFRow row = worksheet.getRow(i);

            product.setUsername(username);
            product.setName(row.getCell(0).getStringCellValue());
            product.setCategory(row.getCell(1).getStringCellValue());
            product.setDescription(row.getCell(2).getStringCellValue());
            product.setMin_bid_amt_seller(row.getCell(3).getNumericCellValue());
            product.setListed_datetime(new Date().toString());
            product.setCurrent_bid_amt(product.getMin_bid_amt_seller());
            productList.add(product);
        }

        try{
            userDetails = userDetailsRepository.findByUsername(productList.get(0).getUsername());
        }
        catch (NullPointerException e){
            return null;
        }

        User finalUserDetails = userDetails;
        productList.forEach(e -> e.setUser(finalUserDetails));

        productRepository.saveAll(productList);
        return "Products Added!!!";
    }
}
