package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExerciseList extends Exercise {

    //The ArrayList of Exercise objects
    private Map<String, Exercise> Exercises = new HashMap<>();

    /**
     * Adds an exercise to the ArrayList of Exercise objects
     * @param exer - An Exercise object
     */
    public void addExercise(Exercise exer){
        this.Exercises.put(exer.getName(), exer);
    }

    /**
     * Gets an exercise that matches the name given by the user
     * @param name - the name of the exercise the user wants to get
     * @return - an Exercise object
     */
    public Exercise getExercise(String name){
        for (String exer : Exercises.keySet()) {
            if (exer.toLowerCase().equals(name.toLowerCase())) return Exercises.get(exer).copy();
        }

        return null;
    }

    /**
     * Returns the whole ArrayList of Exercise objects
     * @return - the ArrayList of Exericse objects;
     */
    public Map<String, Exercise> getAllExercises(){
        return Exercises;
    }


}
