package service;

import Strategy.LowestCostStrategy;
import Strategy.SelectionStrategy;
import model.MenuItem;
import model.Order;
import model.OrderStatus;
import model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodOrderService {
    private final Map<String, Restaurant> restaurants = new HashMap<>();
    private final List<Order> orders = new ArrayList<>();
    private SelectionStrategy strategy = new LowestCostStrategy();

    public void setStrategy(SelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public void onboardRestaurant(String name, double rating, int maxOrders, Map<String, Double> menu) {
        Restaurant r = new Restaurant(name, rating, maxOrders);
        menu.forEach((item, price) -> r.addMenuItem(new MenuItem(item, price)));
        restaurants.put(name, r);
    }

    public void updateMenu(String restaurantName, String itemName, double price, boolean isAdd) {
        Restaurant r = getRestaurant(restaurantName);
        if (isAdd) r.addMenuItem(new MenuItem(itemName, price));
        else r.updateMenuItem(itemName, price);
    }

    public String placeOrder(String user, Map<String, Integer> items) {
        List<Restaurant> all = new ArrayList<>(restaurants.values());
        Restaurant chosen = strategy.select(all, items);
        if (chosen == null) return "Cannot assign the order";

        if (!chosen.hasCapacity()) {
            return "Cannot assign the order: Restaurant capacity full";
        }

        chosen.acceptOrder();
        Order o = new Order(user, items, chosen);
        orders.add(o);
        return "Order assigned to " + chosen.getName();
    }


    public void markOrderComplete(String restaurantName, String userName) {
        for (Order order : orders) {
            if (order.getRestaurant().getName().equals(restaurantName) &&
                    order.getUser().equals(userName) &&
                    order.getStatus() == OrderStatus.ACCEPTED) {

                order.markComplete();
                return;
            }
        }
    }

    private Restaurant getRestaurant(String name) {
        Restaurant r = restaurants.get(name);
        if (r == null) throw new IllegalArgumentException("Restaurant not found");
        return r;
    }
}