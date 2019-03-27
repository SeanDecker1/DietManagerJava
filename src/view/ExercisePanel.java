package view;

import controller.DietController;
import model.Exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExercisePanel extends JPanel {
    private DietController dietController;
    private ViewMediator viewMediator;

    JTextField exerciseNameField;
    JTextField caloriesField;
    

    public ExercisePanel(DietController dietController, ViewMediator viewMediator) {

        this.dietController = dietController;
        this.viewMediator = viewMediator;

        setLayout(new GridLayout(3,1));

        JPanel exercisePanel = new JPanel();
        exercisePanel.setLayout(new BoxLayout(exercisePanel, BoxLayout.Y_AXIS));

        JPanel exerciseNamePanel = new JPanel();
        exerciseNamePanel.setLayout(new FlowLayout());
        JLabel exerciseNameLabel = new JLabel("Exercise Name");
        exerciseNameField = new JTextField();
        exerciseNameField.setText("");
        exerciseNameField.setPreferredSize(new Dimension(150, 20));
        exerciseNamePanel.add(exerciseNameLabel);
        exerciseNamePanel.add(exerciseNameField);

        JPanel caloriesPanel = new JPanel();
        caloriesPanel.setLayout(new FlowLayout());
        JLabel caloriesLabel = new JLabel("Calories");
        caloriesField = new JTextField();
        caloriesField.setText("");
        caloriesField.setPreferredSize(new Dimension(150, 20));
        caloriesPanel.add(caloriesLabel);
        caloriesPanel.add(caloriesField);

        JButton addExerciseButton = new JButton("Add Exercise");
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String exerciseName = exerciseNameField.getText();
                    dietController.addExercise(exerciseName, Double.parseDouble(caloriesField.getText()));
                    resetPanel();
                    viewMediator.updateLogPanel();
                    JOptionPane.showMessageDialog(null,
                            "Exercise successfully added: " + exerciseName);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Exercise failed to add. Please ensure all values are valid.");
                }
            }
        });

        exercisePanel.add(exerciseNamePanel);
        exercisePanel.add(caloriesPanel);
        exercisePanel.add(addExerciseButton);
        add(exercisePanel);
    }

    public void resetPanel() {
        exerciseNameField.setText("");
        caloriesField.setText("");
    }
}
