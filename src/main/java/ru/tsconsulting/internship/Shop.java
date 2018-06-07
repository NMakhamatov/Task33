package ru.tsconsulting.internship;

public class Shop {
    public volatile static int storage = 1000;

    public  static int sell(int goodsAmount) {
        int result;
        if (storage < goodsAmount) {
            result = storage;
            storage = 0;
            return result;
        } else {
            storage -= goodsAmount;
            return goodsAmount;
        }
    }
}
