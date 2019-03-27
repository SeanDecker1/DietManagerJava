package view;

import controller.DietController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class LogPanel extends JPanel {

    private DietController dietController;
    private ViewMediator viewMediator;

    private Set<String> foodList;
    private Set<String> exerciseList;

    private JTextField servingField;
    private JTextField timeField;

    public LogPanel(DietController dietController, ViewMediator viewMediator) {

        this.dietController = dietController;
        this.viewMediator = viewMediator;
            viewMediator.setLogPanel(this);

        foodList = dietController.getFoodListKeys();
        exerciseList = dietController.getExerciseNames();

        setLayout(new GridLayout(4,1));

        JPanel foodLogPanel = createFoodLogPanel();
        JPanel exerciseLogPanel = createExerciseLogPanel();

        add(foodLogPanel);
        add(new JPanel());
        add(exerciseLogPanel);

    }

    private JPanel createFoodLogPanel() {
        JPanel foodLogPanel = new JPanel();
        foodLogPanel.setLayout(new BoxLayout(foodLogPanel, BoxLayout.Y_AXIS));

        JPanel foodRow = new JPanel();
        JLabel foodLabel = new JLabel("Food");
        JComboBox foodComboBox = new JComboBox(foodList.toArray());
        foodRow.add(foodLabel);
        foodRow.add(foodComboBox);

        JPanel servingRow = new JPanel();
        JLabel servingLabel = new JLabel("Servings");
        servingField = new JTextField("");
        servingField.setPreferredSize(new Dimension(30, 20));
        servingRow.add(servingLabel);
        servingRow.add(servingField);

        JButton logFoodButton = new JButton("Log Food");
        logFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dietController.logFood(foodComboBox.getSelectedItem().toString(),
                            Double.parseDouble(servingField.getText()),
                            dietController.getCurrentDate());
                    resetPanel();
                    viewMediator.updateDailyValues();
                    viewMediator.updateDailyCharts();
                    JOptionPane.showMessageDialog(null,
                            "Food successfully logged");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Food failed to log, please ensure your serving value is valid.");
                }
            }
        });

        foodLogPanel.add(foodRow);
        foodLogPanel.add(servingRow);
        foodLogPanel.add(logFoodButton);

        return foodLogPanel;
    }

    private JPanel createExerciseLogPanel() {
        JPanel exerciseLogPanel = new JPanel();
        exerciseLogPanel.setLayout(new BoxLayout(exerciseLogPanel, BoxLayout.Y_AXIS));

        JPanel exerciseRow = new JPanel();
        JLabel exerciseLabel = new JLabel("Exercise");
        JComboBox exerciseComboBox = new JComboBox(exerciseList.toArray());
        exerciseRow.add(exerciseLabel);
        exerciseRow.add(exerciseComboBox);

        JPanel timeRow = new JPanel();
        JLabel timeLabel = new JLabel("Time (m)");
        timeField = new JTextField("");
        timeField.setPreferredSize(new Dimension(30, 20));
        timeRow.add(timeLabel);
        timeRow.add(timeField);

        JButton logExerciseButton = new JButton("Log Exercise");

        logExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dietController.logExercise(exerciseComboBox.getSelectedItem().toString(),
                            Double.parseDouble(timeField.getText()),
                            dietController.getCurrentDate());
                    resetPanel();
                    viewMediator.updateDailyValues();
                    viewMediator.updateDailyCharts();
                    JOptionPane.showMessageDialog(null,
                            "Exercise successfully logged");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Exercise failed to log, please ensure your time value is valid.");
                }
            }
        });

        exerciseLogPanel.add(exerciseRow);
        exerciseLogPanel.add(timeRow);
        exerciseLogPanel.add(logExerciseButton);

        return exerciseLogPanel;
    }

    public void resetPanel() {
        servingField.setText("");
        timeField.setText("");
        updateUI();
    }

    public void updatePanel() {
        foodList = dietController.getFoodListKeys();
        exerciseList = dietController.getExerciseNames();
        removeAll();

        JPanel foodLogPanel = createFoodLogPanel();
        JPanel exerciseLogPanel = createExerciseLogPanel();

        add(foodLogPanel);
        add(new JPanel());
        add(exerciseLogPanel);

        updateUI();

    }
}
