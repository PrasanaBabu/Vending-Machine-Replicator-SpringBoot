package com.ford.springbootstart.Service;

import com.ford.springbootstart.Entity.Balance;
import com.ford.springbootstart.Entity.Change;
import com.ford.springbootstart.Entity.Coin;
import com.ford.springbootstart.Entity.Product;
import com.ford.springbootstart.Exceptions.InsufficientBalanceException;
import com.ford.springbootstart.Exceptions.NoSuchProductException;
import com.ford.springbootstart.Exceptions.OutOfStockException;
import com.ford.springbootstart.Repository.ProductRepo;
import com.ford.springbootstart.Utility.CoinValueGenerator;
import com.ford.springbootstart.Utility.CoinValueGeneratorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ford.springbootstart.Constants.CoinConstants.*;

@Service
public class ProductService {


    ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    public void insertProduct(Product product){
        productRepo.save(product);
    }

    public Product getProductById(int id) {
        return productRepo.findById(id).get();
    }

    public Product dispatchProduct(int id) throws InsufficientBalanceException, OutOfStockException, NoSuchProductException {

        Optional<Product> productOptional = productRepo.findById(id);

        if (productOptional.isPresent()){
            Double currentBalance = Balance.getBalance();
            Product productToDispatch = productOptional.get();

            balanceSufficientCheck(productToDispatch);
            stockAvailableCheck(productToDispatch);

            productToDispatch.setQuantity(productToDispatch.getQuantity() - 1);
            productRepo.save(productToDispatch);
            Balance.setBalance(currentBalance - productToDispatch.getpCost());
            return productToDispatch;
        }
        throw new NoSuchProductException("No Product Exists for given Id");
    }

    private static void stockAvailableCheck(Product productToDispatch) throws OutOfStockException {
        if (productToDispatch.getQuantity() == 0){
            throw new OutOfStockException("Product Not Available");
        }
    }

    private static void balanceSufficientCheck(Product productToDispatch) throws InsufficientBalanceException {
        Double currentBalance = Balance.getBalance();
        if (currentBalance< productToDispatch.getpCost()){
            throw new InsufficientBalanceException("Balance not enough add more funds");
        }
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }


    public void processingInsertedCoin(Coin coin) {
        CoinValueGenerator coinValueGenerator = new CoinValueGeneratorImpl();
        double valueOfCoin = coinValueGenerator.provideValue(coin);

        if(inValidCoin(valueOfCoin)){
            Change.push(coin);
        }
        else {
            Acceptor acceptor = new AcceptorImpl();
            Double newBalance = acceptor.addBalance(valueOfCoin);
            Displayer.setCurrentDisplay("Current Balance = " + newBalance);
        }
    }
    private static boolean inValidCoin(double valueOfCoin) {
        return valueOfCoin == 0;
    }

    public List<Coin> returnChange(){

        Double currentBalance = Balance.getBalance();
        int[] coinValues = getCoinValuesToGive();
        Coin[] coins = getCoins();
        for (int index = 0; index < coinValues.length; index++){
            while (currentBalance >= coinValues[index] ){
                Balance.setBalance(currentBalance - coinValues[index]);
                Change.push(coins[index]);
                currentBalance = Balance.getBalance();
            }
        }

        List<Coin> coinsToReturn = Change.pop();
        return coinsToReturn;
    }

    private static Coin[] getCoins() {
        return new Coin[]{
                QUARTER_COIN,
                DIME_COIN,
                NICKEL_COIN,
                PENNY_COIN
        };
    }

    private static int[] getCoinValuesToGive() {
        return new int[]{QUARTER_VALUE, DIME_VALUE, NICKEL_VALUE, PENNY_VALUE};
    }

    public String greet() {

        return "Hello";
    }
}
