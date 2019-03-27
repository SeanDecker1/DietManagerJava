package view;

import controller.DietController;
import model.BasicFood;
import model.DietLog;
import model.Exercise;
import model.Food;

import java.io.Console;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class DietView {

    DietController dc;
    ViewMediator viewMediator;
    Scanner scan = new Scanner(System.in);
    String[] foodInfo = new String[5];
    String selectedDate = "";
    boolean todaySelected = true;

    public DietView() {
        dc = new DietController();
        viewMediator = new ViewMediator();
        DietManagerFrame dietManagerFrame = new DietManagerFrame("Diet Manager", dc, viewMediator);
        System.out.println("Welcome to Diet Manager!\n");
        selectedDate = dc.getCurrentDate();
        introMessage();
        initPromptUser();
    }

    private void introMessage() {
        System.out.println("Current Diet Log Date: " + selectedDate);
        System.out.println("Please select numbers between 1-9 to execute desired functionality");
        System.out.println("1: Add a food\n2: Add a Recipe\n3: Log a Food\n4: Set Daily Goals\n5: Set Current Weight\n6: View Log\n7: Remove Food from Log \n8: Add an Exercise \n9: Log an Exercise \n10: Remove Exercise from log \n11: Change Date\n12: Save\n13: Exit\n");
        System.out.print("Please enter a number: ");
    }

    private void initPromptUser() {
        String userInput = "";
        while((userInput = scan.nextLine()) != "") {
            try {
                int input = Integer.parseInt(userInput);

                switch (input) {
                    case 1:
                        buildFood();
                        break;
                    case 2:
                        buildRecipe();
                        break;
                    case 3:
                        logFood();
                        break;
                    case 4:
                        setGoals();
                        break;
                    case 5:
                        setPersonalInfo();
                        break;
                    case 6:
                        getLogInfo(selectedDate);
                        break;
                    case 7:
                        removeFoodFromLog();
                        break;
                    case 8:
                        buildExercise();
                        break;
                    case 9:
                        logExercise();
                        break;
                    case 10:
                        removeExerciseFromLog();
                        break;
                    case 11:
                        changeDate();
                    case 12:
                        System.out.println("Saving...");
                        dc.writeCSV();
                        System.out.println("Your data has been saved!");
                        break;
                    case 13:
                        dc.writeCSV();
                        System.out.println("Thanks for using Diet Manager! Have a great day! :)");
                        System.exit(0);
                    default:
                        System.out.println("Not a valid number");
                        break;
                }
                System.out.print("\n");
                introMessage();
            } catch (NumberFormatException nfe) {
                System.out.println("Please Enter a valid number");
            }
        }
    }

    private void buildExercise(){
        printExerciseList();
        String exerciseElement;
        String exerciseName;
        double caloriesBurned;

        System.out.println("Name of Exercise: ");
        exerciseElement = scan.nextLine();
        exerciseName = exerciseElement;

        while(dc.searchExerciseList(exerciseName.toLowerCase()) != null) { // food already exists by that name in the list
            System.out.println("Sorry, that Exercise already exists in the system.");
            System.out.print("Enter q to quit or please enter another name: ");
            exerciseName = scan.nextLine();
            if(exerciseName.equals("q")) {
                System.out.print("\n");
                return;
            }
        }

        // Calories Burned entry
        System.out.print("Calories Burned for a 100 pound person: ");
        while(!scan.hasNextDouble()) {
            System.out.println("Calories Burned must be a number (ex. 180.0)");
            System.out.print("Please enter a number: ");
            scan.nextLine();
        }
        exerciseElement = scan.nextLine();
        caloriesBurned = Double.parseDouble(exerciseElement);

        dc.addExercise(exerciseName, caloriesBurned);
    }

    public void printExerciseList() {
        System.out.println("=======================AVAILABLE Exercises=======================");
        for(String k : dc.getExerciseNames()) {
            System.out.println(k);
        }
        System.out.print("\n");
    }

    private void buildFood() {
        printFoodList();
        String foodElement;
        String name;
        int calories;

        double carbs;
        double protein;
        double fats;

        System.out.print("Name of Food: ");
        foodElement = scan.nextLine();
        name = foodElement;

        // Here we will need to use the dietController to return us the FoodList list
        // then check the name passed against food list that was returned
        // before the user begins to fill out anything

        while(dc.searchFoodList(name.toLowerCase()) != null) { // food already exists by that name in the list
            System.out.println("Sorry, that food already exists in the system.");
            System.out.print("Enter q to quit or please enter another name: ");
            name = scan.nextLine();
            if(name.equals("q")) {
                System.out.print("\n");
                return;
            }
        }

        // Calorie entry
        System.out.print("Calories: ");
        while(!scan.hasNextInt()) {
            System.out.println("Calories must be a whole number");
            System.out.print("Please enter a number: ");
            scan.nextLine();
        }
        foodElement = scan.nextLine();
        calories = Integer.parseInt(foodElement);

        // Fat entry
        System.out.print("Fat: ");
        while(!scan.hasNextDouble()) {
            System.out.println("Please enter a valid number. Ex.) 10.5");
            System.out.print("Please try again: ");
            scan.nextLine();
        }
        foodElement = scan.nextLine();
        fats = Double.parseDouble(foodElement);


        // Carb entry
        System.out.print("Carbs: ");
        while(!scan.hasNextDouble()) {
            System.out.println("Please enter a valid number. Ex.) 10.5");
            System.out.print("Please try again: ");
            scan.nextLine();
        }
        foodElement = scan.nextLine();
        carbs = Double.parseDouble(foodElement);

        // Protein entry
        System.out.print("Protein: ");
        while(!scan.hasNextDouble()) {
            System.out.println("Please enter a valid number. Ex.) 10.5");
            System.out.print("Please try again: ");
            scan.nextLine();
        }
        foodElement = scan.nextLine();
        protein = Double.parseDouble(foodElement);

        dc.addBasicFood(name, calories, fats, carbs, protein );

        System.out.println("Your food has been added!");
    }

    private void buildRecipe() {

        String userInput;
        String name;
        String food;
        String servings;
        Boolean finishRecipe = false;
        ArrayList<String> recipeList = new ArrayList<String>();
        ArrayList<String> servingList = new ArrayList<String>();

        System.out.print("Please enter the recipe name: ");
        userInput = scan.nextLine();
        name = userInput;

        // Loops of the recipe already exists
        while ( dc.searchFoodList( name.toLowerCase() ) != null ) {

            System.out.println("Recipe can not be created - recipe already exists!");
            System.out.print("Please enter another recipe name, or enter 'q' to quit: ");
            name = scan.nextLine();

            if ( name.equals("q") ) {

                System.out.print("\n");
                return;

            }

        }

        // Prints the entire list of foods
        printFoodList();

        System.out.print("Please enter the name of the food from the list above to add to your recipe: ");
        userInput = scan.nextLine();
        food = userInput;

        while ( !finishRecipe ) {

            // Loops if the food entered does not exist
            while ( dc.searchFoodList( food.toLowerCase() ) == null ) {

                System.out.println("Food can not be added - food does not exist!");
                System.out.print("Please enter another food, or enter 'q' to quit: ");
                food = scan.nextLine();

                if ( food.equals("q") ) {

                    System.out.print("\n");
                    return;

                }

            }

            System.out.print("Please enter the number of servings present in the recipe: ");

            // Checks if the user entered a valid input
            while( !scan.hasNextDouble() ) {

                System.out.println("Invalid entry - must enter a number");
                System.out.print("Please try again: ");
                scan.next();

            }

            userInput = scan.nextLine();
            servings = userInput;

            // Adds the string name and servings of the food to the arrayList
            recipeList.add(food);
            servingList.add(servings);
            System.out.println("Food added to recipe!");

            System.out.print("Enter another food, or enter 'q' to quit and save recipe: ");
            userInput = scan.nextLine();
            food = userInput;

            // If the user chooses to quit, the recipe is added to the list and the loop ends
            if ( food.equals("q") ) {

                System.out.print("\n");
                dc.addRecipe(name, recipeList, servingList);
                System.out.println("Recipe saved!!");
                finishRecipe = true;

            }

        }

    }

    public void printFoodList() {
        System.out.println("=======================AVAILABLE FOODS=======================");
        for(String k : dc.getFoodListKeys()) {
            System.out.println(k);
        }
        System.out.print("\n");
    }

    private double getDoubleInput(String message) {
        String inputString;
        double inputDouble;
        System.out.print(message);
        while(!scan.hasNextDouble()) {
            System.out.println("Please enter a valid number. Ex.) 10.5");
            System.out.print("Please try again: ");
            scan.nextLine();
        }
        inputString = scan.nextLine();
        inputDouble = Double.parseDouble(inputString);
        return inputDouble;
    }

    private int getIntInput(String message) {
        String inputString;
        int inputInt;
        System.out.print(message);
        while(!scan.hasNextInt()) {
            System.out.println("Please enter a valid number. Ex.) 10");
            System.out.print("Please try again: ");
            scan.next();
        }
        inputString = scan.nextLine();
        inputInt = Integer.parseInt(inputString);
        return inputInt;
    }

    private void logFood() {
        String foodElement;
        String name;
        double servingCount;

        System.out.print("\n");
        printFoodList();

        //food name entry
        System.out.print("Name of Food: ");
        foodElement = scan.nextLine();
        name = foodElement;

        while(dc.searchFoodList(name.toLowerCase()) == null) {
            System.out.println("Sorry, that food does not exist in the system.");
            System.out.print("Enter q to quit or please enter another name: ");
            name = scan.nextLine();
            if(name.equals("q")) {
                System.out.print("\n");
                return;
            }
        }

        //serving count entry
        System.out.print("Number of Servings: ");
        while(!scan.hasNextDouble()) {
            System.out.println("Please enter a valid number. Ex.) 10.5");
            System.out.print("Please try again: ");
            scan.nextLine();
        }
        foodElement = scan.nextLine();
        servingCount = Double.parseDouble(foodElement);

        dc.logFood(name, servingCount, selectedDate);
        System.out.println("Your food has been logged!");
    }

    private void logExercise(){
        String exerciseElement;
        String name;
        double time;

        System.out.print("\n");
        printExerciseList();

        //exercise name entry
        System.out.print("Name of Exercise: ");
        exerciseElement = scan.nextLine();
        name = exerciseElement;

        while(dc.searchExerciseList(name.toLowerCase()) == null) {
            System.out.println("Sorry, that exercise does not exist in the system.");
            System.out.print("Enter q to quit or please enter another name: ");
            name = scan.nextLine();
            if(name.equals("q")) {
                System.out.print("\n");
                return;
            }
        }

        //serving count entry
        System.out.print("Duration of exercise (in minutes): ");
        while(!scan.hasNextDouble()) {
            System.out.println("Please enter a valid number. Ex.) 10.5");
            System.out.print("Please try again: ");
            scan.nextLine();
        }
        exerciseElement = scan.nextLine();
        time = Double.parseDouble(exerciseElement);

        dc.logExercise(name, time, selectedDate);
        System.out.println("Your exercise has been logged!");

    }

    private void setGoals() {
        double calorieGoal = getDoubleInput("Daily Calorie Limit: ");
        double weightGoal = getDoubleInput("Daily Weight Goal (lbs.): ");
        dc.setUserGoals(calorieGoal, weightGoal, selectedDate);
        System.out.println("Your goals have been updated!");
    }

    private void setPersonalInfo() {
        double weight = getDoubleInput("Current Weight (lbs.): ");
        dc.setPersonalInfo(weight, selectedDate);
        System.out.println("Your weight has been updated!");
    }

    private void printNumberedFoodListWithServings(ArrayList<Food> foodList) {
        for(int i = 1; i<=foodList.size(); i++) {
            Food food = foodList.get(i-1);
            System.out.println(i + ".) " + food.getName() + " - " + food.getServing() + " servings");
        }
    }

    private void printExercisesWithTimes(ArrayList<Exercise> exerciseList){
        for(int i = 1; i <= exerciseList.size(); i++){
            Exercise exer = exerciseList.get(i-1);
            System.out.println(i + ".) " + exer.getName() + " - " + exer.getCaloriesBurned());
        }
    }

    private void removeFoodFromLog() {
        ArrayList<Food> foodList = dc.getTodaysLogList();
        System.out.println("Food Log for current day: ");
        printNumberedFoodListWithServings(foodList);
        int intInput = getIntInput("Please enter the number for the food entry you wish to remove:");
        dc.removeFoodFromLog(intInput-1);
        System.out.println("Successfully removed entry from log!");
    }

    private void removeExerciseFromLog(){
        ArrayList<Exercise> exerciseList = dc.getTodaysExerciseLogList();
        System.out.println("Exercise Log for current day: ");
        printExercisesWithTimes(exerciseList);
        int intInput = getIntInput("Please enter the number for the exercise entry you wish to remove:");
        dc.removeExerciseFromLog(intInput-1);
        System.out.println("Successfully removed entry from log!");
    }

    private String getDateFromUser() {
        System.out.print("Please Enter A Date yyyy/mm/dd: ");
        String date = scan.nextLine();
        //while((date = scan.nextLine()) != "") {
        // some kind of validation here. regex?
        // needs to be in format yyyy/mm/dd
        //return date;
        //}
        return date;
    }

    private void getLogInfo(String date) {
        // run function in diet controller to return all nutritional info?
        DietLog desiredLog = dc.getDesiredDietLog(date);
        int totalCalories = desiredLog.getTotalCalories();
        double totalCaloriesBurned = desiredLog.getTotalCaloriesBurned();
        double dailyLimit = desiredLog.getDailyLimit();
        double weight = desiredLog.getDailyWeight();
        double fatPerc = desiredLog.getFatPerc();
        double carbPerc = desiredLog.getCarbPerc();
        double proteinPerc = desiredLog.getProteinPerc();
        double netCals = desiredLog.getNetCals();

        System.out.println("=======================LOGGED INFO FOR DATE=======================");
        System.out.println("Weight: " + weight + " lbs");
        System.out.println("Calorie Limit: " + dailyLimit);
        System.out.println("Total Calories Consumed: " + totalCalories);
        System.out.println("Total Calories Burned: " + totalCaloriesBurned);
        System.out.println("Fat Percentage: " + fatPerc + "%");
        System.out.println("Carb Percentage: " + carbPerc + "%");
        System.out.println("Protein Percentage: " + proteinPerc + "%");

        double calsLeft = desiredLog.calcCalDiff();
        if(calsLeft >= 0) {
            System.out.println("You have " + calsLeft + " calories left!");
        }
        else {
            double exceededCals = totalCalories - dailyLimit;
            System.out.println("You have exceeded your caloric limit by " + exceededCals + " calories!");
        }

        // print out all foods eaten for day.
        for(String foodInfo : desiredLog.getFoodInfo()) {
            System.out.println(foodInfo);
        }

        for(String exerInfo: desiredLog.getExerciseInfo()){
            System.out.println(exerInfo);
        }
    }

    private void changeDate() {
        System.out.print("\nPlease enter a date (enter 'today' to change to today's date, or 'q' to go back): ");
        String input = scan.nextLine();

        while (input.equals("")) {
            input = scan.nextLine();
        }

        switch (input) {
            case "q":
                return;

            case "today":
                selectedDate = dc.getCurrentDate();
                todaySelected = true;
                break;

            default:
                selectedDate = input;
                todaySelected = false;
                break;
        }
    }
}
