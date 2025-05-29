package org.example;

import model.MenuItem;
import service.FoodOrderService;
import Strategy.HighestRatingStrategy;
import Strategy.LowestCostStrategy;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FoodOrderService service = new FoodOrderService();

        // Onboard Restaurant R1
        Map<String, Double> menuR1 = new HashMap<>();
        menuR1.put("Veg Biryani", 100.0);
        menuR1.put("Chicken Biryani", 150.0);
        service.onboardRestaurant("R1", 4.5, 5, menuR1);

        // Onboard Restaurant R2
        Map<String, Double> menuR2 = new HashMap<>();
        menuR2.put("Idli", 10.0);
        menuR2.put("Dosa", 50.0);
        menuR2.put("Veg Biryani", 80.0);
        menuR2.put("Chicken Biryani", 175.0);
        service.onboardRestaurant("R2", 4.0, 5, menuR2);

        // Onboard Restaurant R3
        Map<String, Double> menuR3 = new HashMap<>();
        menuR3.put("Idli", 15.0);
        menuR3.put("Dosa", 30.0);
        menuR3.put("Gobi Manchurian", 150.0);
        menuR3.put("Chicken Biryani", 175.0);
        service.onboardRestaurant("R3", 4.9, 1, menuR3);

        // Update Menu R1 - ADD Chicken65
        service.updateMenu("R1", "Chicken65", 250.0, true);

        // Update Menu R2 - UPDATE Chicken Biryani price
        service.updateMenu("R2", "Chicken Biryani", 150.0, false);

        // Order 01 - Ashwin
        service.setStrategy(new LowestCostStrategy());
        Map<String, Integer> order1 = new HashMap<>();
        order1.put("Idli", 3);
        order1.put("Dosa", 1);
        System.out.println(service.placeOrder("Ashwin", order1)); // ➞ R3

        // Order 02 - Harish
        Map<String, Integer> order2 = new HashMap<>();
        order2.put("Idli", 3);
        order2.put("Dosa", 1);
        System.out.println(service.placeOrder("Harish", order2)); // ➞ R2

        // Order 03 - Shruthi (Highest Rating Strategy)
        service.setStrategy(new HighestRatingStrategy());
        Map<String, Integer> order3 = new HashMap<>();
        order3.put("Veg Biryani", 3);
        order3.put("Dosa", 1);
        System.out.println(service.placeOrder("Shruthi", order3)); // ➞ R1

        // Complete Order 01
        service.markOrderComplete("R3", "Ashwin");

        // Order 04 - Harish again
        service.setStrategy(new LowestCostStrategy());
        Map<String, Integer> order4 = new HashMap<>();
        order4.put("Idli", 3);
        order4.put("Dosa", 1);
        System.out.println(service.placeOrder("Harish", order4)); // ➞ R3

        // Order 05 - Diya (invalid item)
        Map<String, Integer> order5 = new HashMap<>();
        order5.put("Idli", 3);
        order5.put("Paneer Tikka", 1);
        System.out.println(service.placeOrder("Diya", order5)); // ➞ Cannot assign the order
    }
}