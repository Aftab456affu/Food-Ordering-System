package Strategy;

import model.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LowestCostStrategy implements SelectionStrategy {
    public Restaurant select(List<Restaurant> candidates, Map<String, Integer> items) {
        return candidates.stream()
                .filter(r -> r.canFulfill(items))
                .min(Comparator.comparingDouble(r -> r.calculateOrderCost(items)))
                .orElse(null);
    }
}

