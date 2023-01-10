package com.ford.springbootstart.Service;

public class Displayer {

    private static String currentDisplay = "Insert Coin";

//    public static String getCurrentDisplay() {
//        return currentDisplay;
//    }

    public static void setCurrentDisplay(String currentDisplay) {
        Displayer.currentDisplay = currentDisplay;
    }

}

