package ru.tsconsulting.internship;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Shopping {
    final int amount;
    final CyclicBarrier barrier;
    final Buyer[] buyers;
    volatile boolean isEnd = false;

    Shopping(int amount) {
        buyers = new Buyer[amount];
        this.amount = amount;
        barrier = new CyclicBarrier(amount);

        for (int i = 0; i < amount; i++) {
            buyers[i] = new Buyer(i);
            new Thread(buyers[i]).start();
        }
    }

    class Buyer implements Runnable {

        private final int id;
        private int goodsAmount;
        private int purchasesAmount;
        Random random = new Random();


        Buyer(int id) {
            this.id = id + 1;
        }

        public void run() {
            while (!isEnd || barrier.getNumberWaiting() > 0) {
                synchronized (Shop.class) {
                    if (Shop.storage > 0) buy();
                }
                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    ex.getMessage();
                } catch (BrokenBarrierException ex) {
                    ex.getMessage();
                }
                if (Shop.storage == 0) isEnd = true;
            }
            if (barrier.getNumberWaiting() > 0) barrier.reset();
        }

        private void buy() {
            int wantToBuy = random.nextInt(10) + 1;
            int soldNow = Shop.sell(wantToBuy);
            goodsAmount += soldNow;
            purchasesAmount++;
        }

        @Override
        public String toString() {
            return "---------Покупатель № " + id + " купил количество товаров: " + goodsAmount
                    + " за количество покупок: " + purchasesAmount + "---------\n";
        }
    }

    void printShoppingResults() {
        while (true) {
            if (isEnd) {
                System.out.println(Arrays.toString(buyers));
                break;
            }

        }
    }
}
