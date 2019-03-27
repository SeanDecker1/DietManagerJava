package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Food {

    protected String name;
    protected int calories;
    protected double protein;
    protected double fat;
    protected double carbs;
    protected double serving;
    protected List<Food> children = new ArrayList<>();
    protected List<String> recipeServings = new ArrayList<>();
    
    /**
     * Food constructor
     * @param name - name of the food
     */
    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getServing() {
        return serving;
    }

    public void setServing(double serving) {
        this.serving = serving;
    }

    public abstract List<Food> getChildren();

    public abstract Food getChild(int index);

    public abstract void addChild(Food food, String serving);

    public abstract void removeChild(Food foodToRemove);

    public abstract String buildCSVFormat();

    public abstract Food copy();

} // end of Food Class