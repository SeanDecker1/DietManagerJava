package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.*;

public class DietLog {

	protected int totalCalories;
	protected double totalCaloriesBurned;
	protected double totalFat;
	protected double totalProtein;
	protected double totalCarbs;
	protected double dailyLimit;
	protected double dailyWeight;
	protected String currentDate;

	private int fatPerc;
	private int carbPerc;
	private int proteinPerc;

	// Used to format date string
	protected DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	protected Date date = new Date();

	private ArrayList<Food> dailyFood = new ArrayList<>();
	private ArrayList<Exercise> dailyExercise = new ArrayList<>();

	// Constructor for daily log object
	public DietLog() {
		currentDate = dateFormat.format(date);
	}

	// Complies the total amount of calories
	public int getTotalCalories() {
		return totalCalories;
	}

	public double getTotalCaloriesBurned(){ return totalCaloriesBurned;}

	// Compiles the total amount of fat
	public double getTotalFat() {
		return totalFat;
	}

	// Compiles the total amount of protein
	public double getTotalProtein() {
		return totalProtein;
	}

	// Compiles the total amount of carbs
	public double getTotalCarbs() {
		return totalCarbs;
	}

	public double getDailyWeight() {
		return dailyWeight;
	}

	public double getDailyLimit() { return dailyLimit; }

	public double getFatPerc() {
		return fatPerc;
	}

	public double getCarbPerc() {
		return carbPerc;
	}

	public double getProteinPerc() {
		return proteinPerc;
	}

	// Sets the user's calorie limit for the day
	public void setDailyLimit(double calorieLimit) {
		dailyLimit = calorieLimit;
	}

	// Sets the user's weight for the day
	public void setDailyWeight(double weight) {
		dailyWeight = weight;
	}

	//gets the net difference of calories consumed vs burned
	public double getNetCals(){
		return totalCalories - totalCaloriesBurned;
	}

	// Adds a food to the list of foods consumed
	public void logFood(Food foodItem, double servings) {
		foodItem.setServing(servings);
		dailyFood.add(foodItem);
	}

	/**
	 * The math to calculate the amount of calories burned for the user given their weight and the duration
	 * @param exer The exercise performed
	 * @param time The duration of the exercise in minutes
	 * @return The total number of calories burned by the user
	 */
	public double caloriesBurned(Exercise exer, double time){
		double burnedCals = exer.getCaloriesBurned() * (this.dailyWeight / 100.0) * (time / 60);
		totalCaloriesBurned += burnedCals;
		return burnedCals;
	}

	/**
	 * Adds an exercise to the list of exercises performed
	 * @param exer the Exercise object
	 * @param time The duration of the exercise
	 */
	public void logExercise(Exercise exer, double time){
		double burnedCalories = caloriesBurned(exer, time);

		exer.setCaloriesBurned(burnedCalories);
		exer.setTime(time);
		dailyExercise.add(exer);
	}

	/**
	 * Removes an exercise from the exercises performed
	 * @param index The position of the exercise object that eneds to be removed
	 */
	public void removeExerciseByIndex(int index){
		dailyExercise.remove(index);
	}

	/**
	 * Returns all of the exercises performed by the user for the current date
	 * @return an ArrayList on Exercise Objects
	 */
	public ArrayList<Exercise> getDailyExercise() {
		return dailyExercise;
	}

	/**
	 * Builds an ArrayList of strings for all the Exercises
	 * @return An ArrayList of Strings containing exercise information
	 */
	public ArrayList<String> getExerciseInfo() {
		ArrayList<String> ExerciseInfoList = new ArrayList<>();
		for(Exercise e: dailyExercise){
			String eName = e.getName();
			double calsBurned = e.getCaloriesBurned();
			String exerInfo = "Name: " + eName + "\nCalories: " + calsBurned;
			ExerciseInfoList.add(exerInfo);
		}

		return ExerciseInfoList;

	}

	// Removes a food from the list of foods consumed
	public void removeFood(Food foodItem) {
		dailyFood.remove(foodItem);
	}

	public void removeFoodByIndex(int index) {
		dailyFood.remove(index);
	}

	public ArrayList<Food> getDailyFood() {
		return dailyFood;
	}

	// this is simply going to be extracting information for each food object in the food arraylist
	// and building a new arraylist of Strings
	public ArrayList<String> getFoodInfo() {
		ArrayList<String> foodInfoList = new ArrayList<>();

		for(Food food : dailyFood) {
			String foodName = food.getName();
			int foodCalories = food.getCalories();
			double foodServings = food.getServing();

			String foodInfo = "Name: " + foodName + "\nCalories: " + foodCalories + "\nServings:  " + foodServings;
			foodInfoList.add(foodInfo);
		}

		return foodInfoList;
	}



	public DietLog calcTotals() {
		int totalCals = 0;
		double totalBurned = 0;
		double totalFats = 0;
		double totalProtein = 0;
		double totalCarbs = 0;

		for(Food food : dailyFood) {
			double foodServing = food.getServing();
			totalCals += (food.getCalories() * foodServing);
			totalFats += (food.getFat() * foodServing);
			totalProtein += (food.getProtein() * foodServing);
			totalCarbs += (food.getCarbs() * foodServing);
		}

		for(Exercise e: dailyExercise){
			totalBurned += e.getCaloriesBurned();
		}

		this.totalCalories = totalCals;
		this.totalCaloriesBurned = totalBurned;
		this.totalFat = totalFats;
		this.totalProtein = totalProtein;
		this.totalCarbs = totalCarbs;

		calcPercentages();

		return this;
	}

	public double calcCalDiff() {
		int caloriesEaten = this.totalCalories;
		double caloriesBurned = this.totalCaloriesBurned;
		double caloriesLeft = dailyLimit - caloriesEaten + caloriesBurned;

		return caloriesLeft;
	}

	public void calcPercentages() {
		double totalNutrients = this.totalCarbs + this.totalProtein + this.totalFat;

		// sets to nearest integer
		this.fatPerc = (int) Math.rint(this.totalFat/totalNutrients * 100);
		this.carbPerc = (int) Math.rint(this.totalCarbs/totalNutrients * 100);
		this.proteinPerc = (int) Math.rint(this.totalProtein/totalNutrients * 100);
	}

//	private void getPreviousDay(LocalDate start, LocalDate end) {
//		for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
//			processDate(date);
//		}
//	}
}