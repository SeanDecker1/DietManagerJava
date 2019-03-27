package model;

import java.util.List;

public class BasicFood extends Food {

    public BasicFood(String name) {
        super(name);
        this.calories = 0;
        this.protein = 0;
        this.carbs = 0;
        this.fat = 0;
        this.serving = serving;
    }

    public BasicFood(String name, int calories, double fats, double carbs, double protein) {
        super(name);
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fats;
        this.serving = 1;
    }

    @Override
    public List<Food> getChildren() {
        return null;
    }

    @Override
    public Food getChild(int index) {
        return null;
    }

    @Override
    public void addChild(Food food, String serving) { }

    @Override
    public void removeChild(Food foodToRemove) { }

    @Override
    public String buildCSVFormat() {
        return "b," + name + "," + calories + "," + fat + "," + carbs + "," + protein;
    }

    @Override
    public Food copy() {
        BasicFood copyFood = new BasicFood(name, calories, fat, carbs, protein);
        copyFood.serving = serving;
        return copyFood;
    }

} // end of BasicFood Class
