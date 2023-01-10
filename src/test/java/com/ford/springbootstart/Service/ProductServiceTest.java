package com.ford.springbootstart.Service;

import com.ford.springbootstart.Entity.Balance;
import com.ford.springbootstart.Entity.Change;
import com.ford.springbootstart.Entity.Coin;
import com.ford.springbootstart.Entity.Product;
import com.ford.springbootstart.Exceptions.InsufficientBalanceException;
import com.ford.springbootstart.Exceptions.NoSuchProductException;
import com.ford.springbootstart.Exceptions.OutOfStockException;
import com.ford.springbootstart.Repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ford.springbootstart.Constants.CoinConstants.NICKEL_COIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    ProductRepo productRepo;

    @InjectMocks
    ProductService productService;

    Product testProduct = new Product(1, 10.0, 2, "Cola");

    @Test
    public void sample(){
            when(productRepo.findById(1))
                    .thenReturn(Optional.of(testProduct));
//        given(productRepo.findById(1)).willReturn(Optional.of(new Product(1, 10.0, 2, "Cola")));
        Product productById = productService.getProductById(1);
        assertNotNull(productById);
    }


    @Test
    public void addDataCorrectly(){
        when(productRepo.save(testProduct))
                .thenReturn(testProduct);

        Product productAdded = productService.insertProduct(testProduct);
        assertNotNull(productAdded);

    }

    @Test
    public void returnAllProductsCorrectly(){
        when(productRepo.findAll())
                .thenReturn(Collections.singletonList(testProduct));

        List<Product> allProducts = productService.getProducts();

        assertNotNull(allProducts);
    }

    @Test
    public void dispatchProductCorrectly() throws OutOfStockException, InsufficientBalanceException, NoSuchProductException {
        when(productRepo.findById(1))
                .thenReturn(Optional.of(testProduct));
        Balance.setBalance(10.0);
        Product productAfterDispatch = productService.dispatchProduct(1);

        assertEquals(1, productAfterDispatch.getQuantity());
    }

    @Test
    public void invalidProductTryingToFetch(){
        when(productRepo.findById(100))
                .thenReturn(Optional.empty());
        Balance.setBalance(10.0);

        assertThrows(NoSuchProductException.class,
                ()-> productService.dispatchProduct(100));
    }

    @Test
    public void outOfStockProductTryingToFetch(){
        when(productRepo.findById(2))
                .thenReturn(Optional.of(new Product(2,10.0,0, "Brill")));
        Balance.setBalance(10.0);


        assertThrows(OutOfStockException.class,
                ()-> productService.dispatchProduct(2));

        Balance.setBalance(0.0);


    }

    @Test
    public void balanceInsufficientProductTryingToFetch(){
        when(productRepo.findById(1))
                .thenReturn(Optional.of(testProduct));


        assertThrows(InsufficientBalanceException.class,
                ()-> productService.dispatchProduct(1));
    }


    @Test
    public void insertValidCoinTest(){
        String replyGot = productService.processingInsertedCoin(NICKEL_COIN);
        assertEquals("Coin Inserted", replyGot);
        assertEquals(5.0, Balance.getBalance());
        Balance.setBalance(0.0);
        Change.pop();
    }
    @Test
    public void insertInvalidCoinTest(){
        Balance.setBalance(0.0);
        String replyGot = productService.processingInsertedCoin(new Coin(1.5, 1.4));
        assertEquals("Invalid Coin added", replyGot);
        assertEquals(0.0, Balance.getBalance());
        Change.pop();

    }

    @Test
    public void returnChangeCorrectly(){
        String replyGot = productService.processingInsertedCoin(new Coin(1.5, 1.4));
        Balance.setBalance(5.0);
        List<Coin> testCoinList = new ArrayList<>();
        testCoinList.add(new Coin(1.5, 1.4));
        testCoinList.add(NICKEL_COIN);
        List<Coin> coins = productService.returnChange();
        assertEquals(coins, testCoinList);
    }

    @Test
    public void returnBalanceCorrectly(){
        Balance.setBalance(10.0);
        String currentBalance = productService.getBalance();
        assertEquals("10.0", currentBalance);
    }

    @Test
    public void getScreenAsExpected(){
        Balance.setBalance(10.0);
        productService.insertProduct(testProduct);
        String screenMessage = productService.showScreenMessage();

        System.out.println(screenMessage);

        String msg = "---Products---" +System.lineSeparator()+
                "--------------" +System.lineSeparator()+
                "Balance : 10.0" +System.lineSeparator()+
                "Change : []"+System.lineSeparator();

        assertEquals(msg, screenMessage);
    }

    @Test
    public void deleteProduct(){
        productService.insertProduct(testProduct);
        String deleted = productService.deleteProduct(1);
        assertEquals("Successfully Deleted", deleted);
    }
}