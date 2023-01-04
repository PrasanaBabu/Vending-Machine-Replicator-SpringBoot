package com.ford.springbootstart.Utility;

import com.ford.springbootstart.Entity.Coin;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ford.springbootstart.Constants.CoinConstants.*;

public class CoinValueGeneratorImpl implements CoinValueGenerator {
    Logger logger = Logger.getLogger(CoinValueGeneratorImpl.class.getName());

    @Override
    public double provideValue(Coin coin) {
        if (coin.getThicknessInMM() == PENNY_THICKNESS && coin.getWeightInGrams() == PENNY_WEIGHT) {
            logger.log(Level.FINE,"PENNY COIN ADDED " );
            return PENNY_VALUE;
        }
        else if (coin.getThicknessInMM() == NICKEL_THICKNESS && coin.getWeightInGrams() == NICKEL_WEIGHT) {
            logger.log(Level.FINE,"NICKEL COIN ADDED " );
            return NICKEL_VALUE;
        }

        else if (coin.getThicknessInMM() == DIME_THICKNESS && coin.getWeightInGrams() == DIME_WEIGHT) {
            logger.log(Level.FINE,"DIME COIN ADDED " );
            return DIME_VALUE;
        }

        else if (coin.getThicknessInMM() == QUARTER_THICKNESS && coin.getWeightInGrams() == QUARTER_WEIGHT) {
            logger.log(Level.FINE,"QUARTER COIN ADDED " );
            return QUARTER_VALUE;
        }
        else {

            logger.log(Level.SEVERE,"Invalid Coin " + coin);
            return 0.0;
        }

    }

}

