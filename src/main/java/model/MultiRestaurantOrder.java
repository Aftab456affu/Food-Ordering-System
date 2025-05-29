package model;

import java.util.HashMap;
import java.util.Map;

public class MultiRestaurantOrder {
    private final String user;
    private final Map<String, Map<String, Integer>> restaurantItems;
    private final Map<String, Double> billPerRestaurant = new HashMap<>();

    public MultiRestaurantOrder(String user, Map<String, Map<String, Integer>> restaurantItems) {
        this.user = user;
        this.restaurantItems = restaurantItems;
    }

    public void addBill(String restaurant, double cost) {
        billPerRestaurant.put(restaurant, cost);
    }

    public double getTotalBill() {
        return billPerRestaurant.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<String, Map<String, Integer>> getRestaurantItems() {
        return restaurantItems;
    }

    public String getUser() {
        return user;
    }
}

