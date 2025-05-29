package model;

import java.util.Map;

public class Order {
    private final String user;
    private final Map<String, Integer> items;
    private final Restaurant restaurant;
    private final double totalCost;
    private OrderStatus status;

    public Order(String user, Map<String, Integer> items, Restaurant restaurant) {
        this.user = user;
        this.items = items;
        this.restaurant = restaurant;
        this.totalCost = calculateTotalCost(items, restaurant);
        this.status = OrderStatus.ACCEPTED;
    }

    private double calculateTotalCost(Map<String, Integer> items, Restaurant restaurant) {
        double total = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            double price = restaurant.getPriceForItem(entry.getKey());
            total += price * entry.getValue();
        }
        return total;
    }

    public String getUser() {
        return user;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void markComplete() {
        this.status = OrderStatus.COMPLETED;
        restaurant.completeOrder();
    }
}
