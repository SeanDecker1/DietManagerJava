package controller;

import model.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class DietController {
    private FoodList foodList;
    private ExerciseList exerciseList;
    private CSVParser csv = new CSVParser();
    private User user =  new User();

    public DietController() {
        foodList = csv.readFoodCSV();
        exerciseList = csv.readExerciseCSV();
        user.setLogHistory(csv.readLogCSV(foodList, exerciseList));
    }

    /*
        Given a BasicFood, add it to the global FoodList.
     */
    public void addBasicFood(String name, int calories, double carbs, double proteins, double fats) {
        BasicFood food = new BasicFood(name, calories, carbs, proteins, fats);
        foodList.addFood(food);
    }

    /**
     * Adds an Exercise to the global Exercise list
     * @param name - the name of the exercise
     * @param calsBurned - the number of calories burned for a 100lb person
     */
    public void addExercise(String name, double calsBurned){
        try {
            if(name.contains(",")) {
                throw new Exception("Sorry, you cannot have commas in your exercise name");
            }
            else {
                Exercise exercise = new Exercise(name, calsBurned);
                exerciseList.addExercise(exercise);
                System.out.println("Your exercise has been added!");
            }
        }
        catch(Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    /*
        Given a Recipe, add it to the global FoodList.
     */
    public void addRecipe(String name, ArrayList<String> recipeFoods, ArrayList<String> recipeServings) {
        
    	// Creates a new recipe object
    	Recipe recipe = new Recipe(name);
    	
    	// Loops through arrayList and adds each food object as a child
    	for (int i = 0; i < recipeFoods.size(); i++ ) {
    		recipe.addChild( foodList.findFood( recipeFoods.get(i) ), recipeServings.get(i) );
    	}
    	
    	recipe.addNutritionalInfo();
    	
    	// Adds the recipe to the list of food
    	foodList.addFood(recipe);
    	
    	System.out.println(recipe.getCalories());
    	
    }

    /**
     * Logs a food to the food log for the current day
     * @param foodName - name of the food being logged
     * @param servings - number of servings of the food
     * @return - true if successful, false on failure
     */
    public boolean logFood(String foodName, double servings, String date) {
        Food loggedFood = foodList.findFood(foodName);
        user.logFood(loggedFood, servings, date);
        return true;
    }


    public boolean logExercise(String name, double time, String date){
        Exercise loggedExer = exerciseList.getExercise(name);
        user.logExercise(loggedExer, time, date);
        return true;
    }

    // searches for food in foodlist
    public Food searchFoodList(String nameFood) {
        return foodList.findFood(nameFood);
    }

    public Exercise searchExerciseList(String exerName) {
        return exerciseList.getExercise(exerName);
    }

    public Set<String> getFoodListKeys() {
        return foodList.getFoods().keySet();
    }

    public Set<String> getExerciseNames(){
        return exerciseList.getAllExercises().keySet();
    }

    public void setUserGoals(double calorieLimit, double weightLimit, String date) {
        user.setCaloricLimit(calorieLimit);
        user.setDesiredWeight(weightLimit);
        user.updateLogCalorieLimit(calorieLimit, date);
    }

    // set user's weight
    public void setPersonalInfo(double weight, String date) {
        user.setWeight(weight);
        user.updateLogWeight(weight, date);
    }


    // attempts to find desired DietLog object and returns it
    public DietLog getDesiredDietLog(String date) {
        return user.searchLogHistory(date).calcTotals();
    }

    // returns history of user's dietlog objects
    public Map<String, DietLog> getUserHistory() {
        return user.getLogHistory();
    }


    public String getCurrentDate() {
        return user.getCurrentDate();
    }

    public void setCurrentDate(String date) {
        user.setCurrentDate(date);
    }

    private DietLog getTodaysLog() {
        return user.getTodaysLog();
    }

    public ArrayList<Food> getTodaysLogList() {
        return getTodaysLog().getDailyFood();
    }

    public ArrayList<Exercise> getTodaysExerciseLogList(){
        return getTodaysLog().getDailyExercise();
    }

    public void removeFoodFromLog(int index) {
        getTodaysLog().removeFoodByIndex(index);
    }

    public void removeExerciseFromLog(int index) {
        getTodaysLog().removeExerciseByIndex(index);
    }

    public void setUserName(String name) {
        user.setName(name);
    }

    public String getUserName() {
        return user.getName();
    }

    /*
        Write the entire FoodList and Daily DietLog to CSV.
     */
    public void writeCSV() {
        csv.writeFoodList(foodList);
        csv.writeExerciseList(exerciseList);
        csv.writeToLog(user);
    }

}