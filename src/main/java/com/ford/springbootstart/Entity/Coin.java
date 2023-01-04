package com.ford.springbootstart.Entity;

//POJO
public class Coin {
    private final double thicknessInMM;
    private final double weightInGrams;

    public Coin(double thicknessInMM, double weightInGrams) {
        this.thicknessInMM = thicknessInMM;
        this.weightInGrams = weightInGrams;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "thicknessInMM=" + thicknessInMM +
                ", weightInGrams=" + weightInGrams +
                '}';
    }

    public double getThicknessInMM() {
        return thicknessInMM;
    }

    public double getWeightInGrams() {
        return weightInGrams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return Double.compare(coin.thicknessInMM, thicknessInMM) == 0 && Double.compare(coin.weightInGrams, weightInGrams) == 0;
    }

}
