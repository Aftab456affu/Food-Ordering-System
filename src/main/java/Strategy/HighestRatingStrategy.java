package Strategy;

import model.Restaurant;
import java.util.*;

public class HighestRatingStrategy implements SelectionStrategy {
    public Restaurant select(List<Restaurant> candidates, Map<String, Integer> items) {
        return candidates.stream()
                .filter(r -> r.canFulfill(items))
                .max(Comparator.comparingDouble(Restaurant::getRating))
                .orElse(null);
    }
}

