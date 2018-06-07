package ru.tsconsulting.internship;

public class Task3 {

    public static void main(String[] args) {
        int amount;
        try {
            amount = Integer.parseInt(args[0]);
            Shopping shopping = new Shopping(amount);
            shopping.printShoppingResults();
        } catch (NumberFormatException e) {
            System.out.println("Введите корректное количество покупателей");
            e.getMessage();
        }
    }
}
