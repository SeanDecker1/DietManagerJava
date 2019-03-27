package model;

public class Exercise {

    public String name;
    public double caloriesBurned; //calories burned per hour
    public double time;


    /**
     * Default constructor
     */
    public Exercise(){}

    /**
     * The constructor for a single Exercise
     * @param name - A String for the exercise name
     * @param caloriesBurned - a double for the number of calories burned per hour
     */
    public Exercise(String name, double caloriesBurned){
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    /**
     * Sets the name of the exercise
     * @param name - The new String name of the exercise
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the current name of the exercise
     * @return - String name
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the calories burned per hours for this exercise
     * @param caloriesBurned - The new double value
     */
    public void setCaloriesBurned(double caloriesBurned){
        this.caloriesBurned = caloriesBurned;
    }

    /**
     * Get's the current value for calories burned per hour
     * @return - Double value
     */
    public double getCaloriesBurned(){
        return caloriesBurned;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    /**
     * Function to make the object into a string formatted for the csv
     * @return A string formatted for the csv representing the exercise object
     */
    public String buildCSVFormat() {
        return "e," + name + "," + caloriesBurned;
    }

    public Exercise copy() {
        Exercise exerciseCopy = new Exercise(name, caloriesBurned);
        exerciseCopy.time = time;
        return exerciseCopy;
    }


}
