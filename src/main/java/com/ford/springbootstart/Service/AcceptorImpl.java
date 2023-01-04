package com.ford.springbootstart.Service;

import com.ford.springbootstart.Entity.Balance;


public class AcceptorImpl implements Acceptor {


    public static final int MINIMUM_VALUE_TO_ADD = 1;


    @Override
    public Double addBalance(Double value) {

        if(inValidAmountToAdd(value)){
            return -1.0;
        }

        Double currentBalance = Balance.getBalance();
        Double newBalance = currentBalance + value;

        Balance.setBalance(newBalance);

        return Balance.getBalance();
    }

    private static boolean inValidAmountToAdd(Double value) {
        return value < MINIMUM_VALUE_TO_ADD;
    }
}
