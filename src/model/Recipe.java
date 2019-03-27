package model;

import java.util.ArrayList;
import java.util.List;

public class Recipe extends Food {

    /**
     * Food constructor
     *
     * @param name - name of the food
     */
    public Recipe(String name) {
        super(name);
        this.calories = 0;
        this.protein = 0;
        this.carbs = 0;
        this.fat = 0;
    }

    @Override
    public List<Food> getChildren() {
        return this.children;
    }

    @Override
    public Food getChild(int index) {
        return children.get(index);
    }

    @Override
    public void addChild(Food food, String serving) {
        children.add(food);
        recipeServings.add(serving);
        addNutritionalInfo();
    }

    @Override
    public void removeChild(Food foodToRemove) {
        children.remove(foodToRemove);
    }

    @Override
    public String buildCSVFormat() {
        String csv = "r," + name;
        
        for (int i = 0; i < children.size(); i++) {
        	csv = csv + "," + children.get(i).getName() + "," + recipeServings.get(i);
        }
        
        return csv;
    }

    // Adds up the nutritional info and adds those values to the recipe object
    public void addNutritionalInfo() {
        for (int i = 0; i < children.size(); i++) {
            this.calories = this.calories + children.get(i).getCalories();
            this.protein = this.protein + children.get(i).getProtein();
            this.carbs = this.carbs + children.get(i).getCarbs();
            this.fat = this.fat + children.get(i).getFat();
        }

    }

    @Override
    public double getProtein() {
        double val = 0;
        for (int i = 0; i < children.size(); i++) {
            val += children.get(i).getProtein();
        }
        return val;
    }

    @Override
    public double getCarbs() {
        double val = 0;
        for (int i = 0; i < children.size(); i++) {
            val += children.get(i).getCarbs();
        }
        return val;
    }

    @Override
    public int getCalories() {
        int val = 0;
        for (int i = 0; i < children.size(); i++) {
            val += children.get(i).getCalories();
        }
        return val;
    }

    @Override
    public double getFat() {
        double val = 0;
        for (int i = 0; i < children.size(); i++) {
            val += children.get(i).getFat();
        }
        return val;
    }

    @Override
    public Food copy() {
        Recipe copyRecipe = new Recipe(name);
        copyRecipe.serving = serving;
        copyRecipe.children = new ArrayList<>(children);
        return copyRecipe;
    }

} // end of Recipe Class
