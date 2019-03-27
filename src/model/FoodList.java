package model;

import java.util.HashMap;
import java.util.Map;

public class FoodList {
    private Map<String, Food> foods = new HashMap<>();

    public void addFood(Food food) {
        this.foods.put(food.getName(), food);
    }

    public void removeFood(String nameToRemove) {
        // do we want a user to be able to remove food from
        // the list used by every user?
    }

    public Map<String, Food> getFoods() {
        return foods;
    }

    /**
     * Individual food getter
     * @param key - food name
     * @return a food based on it's name by grabbing the food from the hashmap
     */
    public Food findFood(String key) {
        for (String food : foods.keySet()) {
            if (food.toLowerCase().equals(key.toLowerCase())) return foods.get(food).copy();
        }

        return null;
    }
}