package com.ford.springbootstart.Utility;

import com.ford.springbootstart.Entity.Coin;

public interface CoinValueGenerator {
    double provideValue(Coin coin);
}
