package com.ford.springbootstart.Controller;


import com.ford.springbootstart.Entity.Product;
import com.ford.springbootstart.Exceptions.InsufficientBalanceException;
import com.ford.springbootstart.Exceptions.NoSuchProductException;
import com.ford.springbootstart.Exceptions.OutOfStockException;
import com.ford.springbootstart.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class OwnerController {

    private final ProductService productService;

    public OwnerController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/test")
    public String test(){
        return productService.greet();
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") int id){
        return productService.getProductById(id);
    }
    @GetMapping("/all")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping("/new")
    public String addProduct(@RequestBody Product product){
        System.out.println("product = " + product);
        productService.insertProduct(product);
        return "success";
    }

    @PatchMapping("/buy/{id}")
    public String reduceQuantityOfProductById(@PathVariable("id") int id){
        try {
            return String.valueOf(productService.dispatchProduct(id));
        } catch (InsufficientBalanceException | OutOfStockException | NoSuchProductException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "success";
    }







}
