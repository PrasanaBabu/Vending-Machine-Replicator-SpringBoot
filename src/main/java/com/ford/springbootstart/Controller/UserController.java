package com.ford.springbootstart.Controller;

import com.ford.springbootstart.Entity.Balance;
import com.ford.springbootstart.Entity.Change;
import com.ford.springbootstart.Entity.Coin;
import com.ford.springbootstart.Entity.Product;
import com.ford.springbootstart.Exceptions.InsufficientBalanceException;
import com.ford.springbootstart.Exceptions.NoSuchProductException;
import com.ford.springbootstart.Exceptions.OutOfStockException;
import com.ford.springbootstart.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machine")
public class UserController {
    private final ProductService productService;

    public UserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/screen")
    public String screen(){
        List<Product> allProducts = productService.getProducts();
        StringBuilder finalOutput = new StringBuilder();

        String productBanner = "---Products---";


        String separatorBanner = "--------------";
        finalOutput.append(productBanner);
        finalOutput.append(System.lineSeparator());

        for (Product p :allProducts){
            finalOutput.append(p);
            finalOutput.append(System.lineSeparator());
        }

        finalOutput.append(separatorBanner);
        finalOutput.append(System.lineSeparator());

        finalOutput.append("Balance : " + Balance.getBalance());
        finalOutput.append(System.lineSeparator());

        finalOutput.append("Changes : " + Change.getCoins());
        finalOutput.append(System.lineSeparator());


        return String.valueOf(finalOutput);
    }
    @PostMapping("/insertcoin")
    public String addCoin(@RequestBody Coin coin){
        productService.processingInsertedCoin(coin);
        return "Inserted success";
    }

    @GetMapping("/balance")
    public String getBalance(){
        return String.valueOf(Balance.getBalance());
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
