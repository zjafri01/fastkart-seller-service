package com.fastkart.sellerservice.service;

import com.fastkart.sellerservice.model.Product;
import com.fastkart.sellerservice.model.User;
import com.fastkart.sellerservice.repository.ProductRepository;
import com.fastkart.sellerservice.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String addProduct(Product product){
        User userDetails = null;

        try{
            userDetails = userDetailsRepository.findByUsername(product.getUsername());
            product.setUser(userDetails);
            product.setCurrent_bid_amt(product.getMin_bid_amt_seller());
            productRepository.save(product);
        }
        catch (NullPointerException e){
            return null;
        }
        return "Product Added!!!";
    }
}
