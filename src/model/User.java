package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User {

    private double weightGoal;
    private String name;
    private String currentDate;

    public User() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        currentDate = dtf.format(localDate);
        name = "User";
    }

    public Map<String, DietLog> logHistory = new HashMap<String, DietLog>();

    public void setWeight(double val){
        getTodaysLog().setDailyWeight(val);
    }

    public double getWeight(){
        return getTodaysLog().getDailyWeight();
    }

    public void setCaloricLimit(double val){
        getTodaysLog().setDailyLimit(val);
    }

    public double getCaloricLimit(){
        return getTodaysLog().getDailyLimit();
    }

    public void setDesiredWeight(double val){
        this.weightGoal = val;
    }

    public double getDesiredWeight(){
        return weightGoal;
    }

    public void setName(String val){
        this.name = val;
    }

    public String getName(){
        return this.name;
    }

    public void setDayFood(DietLog DL){
        //to be implemented
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String date) {
        currentDate = date;
    }

    public Map<String, DietLog> getLogHistory() {
        return logHistory;
    }

    public void setLogHistory(Map<String, DietLog> logHistory) {
        this.logHistory = logHistory;
    }

    public DietLog getTodaysLog() {
        if(logHistory.get(currentDate) == null) {
            logHistory.put(currentDate, new DietLog());
            return logHistory.get(currentDate);
        }else {
            return logHistory.get(currentDate);
        }
    }

    // not being used
//    public void logFood(Food food, double servings) {
//        getTodaysLog().logFood(food, servings);
//    }

    public void logFood(Food food, double servings, String date) {
        searchLogHistory(date).logFood(food, servings);
    }

    public void logExercise(Exercise exer, double time, String date){
        searchLogHistory(date).logExercise(exer, time);
    }

    public DietLog searchLogHistory(String date) {
        DietLog desiredLog = logHistory.get(date);

        if(desiredLog != null) {
            return desiredLog;
        }
        else {
            desiredLog = new DietLog();

            // getting previous most recent log
            ArrayList<String> sortedLogs = new ArrayList<>(logHistory.keySet());
            sortedLogs.add(date);
            Collections.sort(sortedLogs);
            int previousIndex = sortedLogs.indexOf(date) - 1;

            // if found, use the previous calorie and weight info
            if (previousIndex >= 0 && previousIndex < sortedLogs.size()) {
                DietLog previousLog = logHistory.get(sortedLogs.get(previousIndex));
                desiredLog.setDailyWeight(previousLog.getDailyWeight());
                desiredLog.setDailyLimit(previousLog.getDailyLimit());
            }
            else {
                // or else if not set (value would come back as 0.0), we set default values for them
                desiredLog.setDailyWeight(150.0);
                desiredLog.setDailyLimit(2000.0);
            }

            // add it into the User's history
            logHistory.put(date, desiredLog);

            // return the newly created DietLog object to the user
            return logHistory.get(date);
        }
    }

    public void updateLogCalorieLimit(double calorieLimit, String date) {
        searchLogHistory(date).setDailyLimit(calorieLimit);
    }

    public void updateLogWeight(double weight, String date) {
        searchLogHistory(date).setDailyWeight(weight);
    }
}
