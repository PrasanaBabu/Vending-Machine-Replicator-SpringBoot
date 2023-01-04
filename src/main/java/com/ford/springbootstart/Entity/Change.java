package com.ford.springbootstart.Entity;

import java.util.ArrayList;
import java.util.List;

public class Change {

    private static List<Coin> returnCoins = new ArrayList<>();

    public static void push(Coin coin){
        returnCoins.add(coin);
    }

    public static List<Coin> pop(){

        List<Coin> returnedCoins = returnCoins;
        returnCoins = new ArrayList<>();
        return returnedCoins;
    }
    public static List<Coin> getCoins(){
        return returnCoins;
    }

}
