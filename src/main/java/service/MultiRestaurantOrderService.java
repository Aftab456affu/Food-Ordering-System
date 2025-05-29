package service;

import model.*;

import java.util.*;

public class MultiRestaurantOrderService {
    private final Map<String, Restaurant> restaurants;
    private final UserService userService;

    public MultiRestaurantOrderService(Map<String, Restaurant> restaurants, UserService userService) {
        this.restaurants = restaurants;
        this.userService = userService;
    }

    public String placeSplitOrder(String userName, Map<String, Integer> items) {
        Map<String, Map<String, Integer>> restToItems = new HashMap<>();

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String item = entry.getKey();
            int quantity = entry.getValue();

            Restaurant selected = null;
            for (Restaurant r : restaurants.values()) {
                if (r.canFulfill(Map.of(item, quantity))) {
                    selected = r;
                    break;
                }
            }

            if (selected == null) return "Cannot assign item: " + item;

            restToItems.computeIfAbsent(selected.getName(), k -> new HashMap<>())
                    .put(item, quantity);
        }

        MultiRestaurantOrder order = new MultiRestaurantOrder(userName, restToItems);
        double totalBill = 0;

        for (Map.Entry<String, Map<String, Integer>> entry : restToItems.entrySet()) {
            Restaurant r = restaurants.get(entry.getKey());
            Map<String, Integer> its = entry.getValue();
            double cost = r.calculateOrderCost(its);
            r.acceptOrder();
            order.addBill(r.getName(), cost);
            totalBill += cost;
        }

        User user = userService.getUser(userName);
        user.deduct(totalBill);

        return "Multi-restaurant order placed. Total bill: " + totalBill;
    }
}

