package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Restaurant {
    private final String name;
    private final int maxOrders;
    private Map<String, MenuItem> menu;
    private int currentOrders;
    private final double rating;

    public Restaurant(String name, int maxOrders, Map<String, Double> itemPrices, double rating) {
        this.name = name;
        this.maxOrders = maxOrders;
        this.rating = rating;
        this.currentOrders = 0;
        this.menu = new HashMap<>();
    }

    public Restaurant(String name, double rating, int maxOrders) {

        this.name = name;
        this.maxOrders = maxOrders;
        this.menu = new HashMap<>();
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public boolean canFulfill(Map<String, Integer> items) {
        for (String item : items.keySet()) {
            if (!menu.containsKey(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasCapacity() {
        return currentOrders < maxOrders;
    }

    public double calculateOrderCost(Map<String, Integer> items) {
        double total = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            MenuItem menuItem = menu.get(itemName);
            if (menuItem == null) {
                throw new IllegalArgumentException("Item " + itemName + " not available in restaurant " + name);
            }
            total += menuItem.getPrice() * quantity;
        }
        return total;
    }

    public void acceptOrder() {
        if (currentOrders < maxOrders) {
            currentOrders++;
        } else {
            throw new IllegalStateException("Max orders reached");
        }
    }

    public void completeOrder() {
        if (currentOrders > 0) {
            currentOrders--;
        } else {
            throw new IllegalStateException("No orders to complete");
        }
    }

    public double getRating() {
        return rating;
    }

    public Map<String, MenuItem> getMenu() {
        return Collections.unmodifiableMap(menu);
    }

    public void addMenuItem(MenuItem menuItem) {
        if (menu.containsKey(menuItem.getName())) {
            throw new IllegalArgumentException("Item already exists. Use update for price change.");
        }
        menu.put(menuItem.getName(), menuItem);
    }

    public void updateMenuItem(String itemName, double newPrice) {
        if (!menu.containsKey(itemName)) {
            throw new IllegalArgumentException("Item not found in menu: " + itemName);
        }
        menu.get(itemName).setPrice(newPrice);
    }

    public double getPriceForItem(String itemName) {
        if (!menu.containsKey(itemName)) {
            throw new IllegalArgumentException("Item not found in menu: " + itemName);
        }
        return menu.get(itemName).getPrice();
    }
}
