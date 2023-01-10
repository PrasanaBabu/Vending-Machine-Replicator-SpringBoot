package com.ford.springbootstart.Controller;

import com.ford.springbootstart.Entity.Coin;
import com.ford.springbootstart.Exceptions.InsufficientBalanceException;
import com.ford.springbootstart.Exceptions.NoSuchProductException;
import com.ford.springbootstart.Exceptions.OutOfStockException;
import com.ford.springbootstart.Service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/machine")
public class UserController {
    private final ProductService productService;

    public UserController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping("/test")
//    public String test(){
//        return productService.greet();
//    }
    @GetMapping("/screen")
    public String screen(){
        return productService.showScreenMessage();
    }


    @PostMapping("/insertcoin")
    public String addCoin(@RequestBody Coin coin){
        return productService.processingInsertedCoin(coin);
    }

    @GetMapping("/balance")
    public String getBalance(){
        return productService.getBalance();
    }

    @PatchMapping("/buy/{id}")
    public String reduceQuantityOfProductById(@PathVariable("id") int id){
        try {
            return "Enjoy Your "+ productService.dispatchProduct(id).getName();
        } catch (InsufficientBalanceException | OutOfStockException | NoSuchProductException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/collect-change")
    public String changeProvider(){
        return String.valueOf(productService.returnChange());
//        return "";
    }


}
