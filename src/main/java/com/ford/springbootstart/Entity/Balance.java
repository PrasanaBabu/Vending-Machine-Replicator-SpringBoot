package com.ford.springbootstart.Entity;

public class Balance {

    private static Double balance = 0.0;

    public static Double getBalance() {
        return balance;
    }

    public static void setBalance(Double balanceToSet) {
        balance = balanceToSet;
    }
}
