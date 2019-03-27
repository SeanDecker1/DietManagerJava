package view;

import controller.DietController;
import model.DietLog;
import model.Exercise;
import model.Food;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DailyValuesPanel extends JPanel {

    private DietController dietController;
    private ViewMediator viewMediator;

    private JPanel datePanel;
    private JLabel dateLabel;
    private JTextField dateField;
    private JButton dateButton;

    private JPanel namePanel;
    private JLabel nameLabel;
    private JLabel nameText;

    private JPanel weightPanel;
    private JLabel weightLabel;
    private JLabel weightText;

    private JPanel consCalPanel;
    private JLabel consCalLabel;
    private JLabel consCalText;

    private JPanel expCalPanel;
    private JLabel expCalLabel;
    private JLabel expCalText;

    private JPanel netCalPanel;
    private JLabel netCalLabel;
    private JLabel netCalText;

    private JScrollPane foodLogPane;
    private JScrollPane exerciseLogPane;

    public DailyValuesPanel(DietController dietController, ViewMediator viewMediator) {

        this.dietController = dietController;
        this.viewMediator = viewMediator;

        TitledBorder border = new TitledBorder("Daily Values");
        setBorder(border);

        setLayout(new GridLayout(4,1));

        datePanel = new JPanel();
        dateLabel = new JLabel("Date");
        dateField = new JTextField(dietController.getCurrentDate());
        dateButton = new JButton("Change Date");

        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dietController.setCurrentDate(dateField.getText());
                    viewMediator.updateDailyCharts();
                    viewMediator.updateDailyValues();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Unable to change date, please ensure your date is in YYYY/MM/DD format.");
                }

            }
        });

        datePanel.add(dateLabel);
        datePanel.add(dateField);
        datePanel.add(dateButton);

        namePanel = new JPanel();
        nameLabel = new JLabel("Name: ");
        nameText = new JLabel("User");
        namePanel.add(nameLabel);
        namePanel.add(nameText);

        weightPanel = new JPanel();
        weightLabel = new JLabel("Weight: ");
        weightText = new JLabel("0 lbs.");
        weightPanel.add(weightLabel);
        weightPanel.add(weightText);
        
        consCalPanel = new JPanel();
        consCalLabel = new JLabel("Calories Consumed: ");
        consCalText = new JLabel("");
        consCalPanel.add(consCalLabel);
        consCalPanel.add(consCalText);

        expCalPanel = new JPanel();
        expCalLabel = new JLabel("Calories Expended: ");
        expCalText = new JLabel("");
        expCalPanel.add(expCalLabel);
        expCalPanel.add(expCalText);

        netCalPanel = new JPanel();
        netCalLabel = new JLabel("Net Calories: ");
        netCalText = new JLabel("");
        netCalPanel.add(netCalLabel);
        netCalPanel.add(netCalText);

        JPanel infoPanel = new JPanel();

        infoPanel.add(namePanel);
        infoPanel.add(weightPanel);
        infoPanel.add(consCalPanel);
        infoPanel.add(expCalPanel);
        infoPanel.add(netCalPanel);

        foodLogPane = createFoodLogPane();
        exerciseLogPane = createExerciseLogPane();

        add(datePanel);
        add(infoPanel);
        add(foodLogPane);
        add(exerciseLogPane);

        updateValues();
    }

    private JScrollPane createFoodLogPane() {
        DietLog log = dietController.getDesiredDietLog(dietController.getCurrentDate());

        JPanel foodLogPanel = new JPanel();
        foodLogPanel.setLayout(new BoxLayout(foodLogPanel, BoxLayout.Y_AXIS));
        JScrollPane foodLogScrollPane = new JScrollPane(foodLogPanel);

        ArrayList<Food> dailyFood = log.getDailyFood();
        for (int i = 0; i<dailyFood.size(); i++) {
            Food food = dailyFood.get(i);

            JPanel innerPanel = new JPanel();
            JLabel innerLabel = new JLabel("Food: " + food.getName() + "  Servings: " + food.getServing()
                    + "  Calories: " + food.getCalories());
            JButton innerButton = new JButton("-");
            final int index = i;
            innerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dietController.removeFoodFromLog(index);
                    viewMediator.updateDailyCharts();
                    updateValues();
                }
            });

            innerPanel.add(innerLabel);
            innerPanel.add(innerButton);
            foodLogPanel.add(innerPanel);
        }

       return foodLogScrollPane;

    }

    private JScrollPane createExerciseLogPane() {
        DietLog log = dietController.getDesiredDietLog(dietController.getCurrentDate());

        JPanel exerciseLogPanel = new JPanel();
        exerciseLogPanel.setLayout(new BoxLayout(exerciseLogPanel, BoxLayout.Y_AXIS));
        JScrollPane exerciseLogScrollPane = new JScrollPane(exerciseLogPanel);

        ArrayList<Exercise> dailyExercise = log.getDailyExercise();
        for (int i = 0; i < dailyExercise.size(); i++) {
            Exercise exercise = dailyExercise.get(i);

            JPanel innerPanel = new JPanel();
            JLabel innerLabel = new JLabel("Exercise: " + exercise.getName() + "  Calories Burned: " + exercise.getCaloriesBurned());

            JButton innerButton = new JButton("-");
            final int index = i;
            innerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dietController.removeExerciseFromLog(index);
                    updateValues();
                }
            });

            innerPanel.add(innerLabel);
            innerPanel.add(innerButton);
            exerciseLogPanel.add(innerPanel);
        }

        return exerciseLogScrollPane;
    }



    public void updateValues() {
        DietLog log = dietController.getDesiredDietLog(dietController.getCurrentDate());

        nameText.setText(dietController.getUserName());
        weightText.setText(Double.toString(log.getDailyWeight()) + " lbs.");
        consCalText.setText(Integer.toString(log.getTotalCalories()));
        expCalText.setText(Double.toString(log.getTotalCaloriesBurned()));
        netCalText.setText(Double.toString(log.getNetCals()));

        remove(foodLogPane);
        foodLogPane = createFoodLogPane();
        add(foodLogPane);

        remove(exerciseLogPane);
        exerciseLogPane = createExerciseLogPane();
        add(exerciseLogPane);

        updateUI();
    }

}
