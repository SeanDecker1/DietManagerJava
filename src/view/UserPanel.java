package view;

import controller.DietController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {

    private DietController dietController;
    private ViewMediator viewMediator;

    public UserPanel(DietController dietController, ViewMediator viewMediator) {

        this.dietController = dietController;
        this.viewMediator = viewMediator;

        setLayout(new GridLayout(4,1));

        JPanel nameEntryPanel = createNameEntryPanel();
        add(nameEntryPanel);

        JPanel weightEntryPanel = createWeightEntryPanel();
        add(weightEntryPanel);

        JPanel dailyGoals = createDailyGoalsPanel();
        add(dailyGoals);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dietController.writeCSV();
            }
        });


        add(saveButton);

    }

    public JPanel createNameEntryPanel() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JTextField nameField = new JTextField();
        nameField.setText("Enter Your Name");
        nameField.setPreferredSize(new Dimension(150,20));

        JButton nameButton = new JButton("Submit");
        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dietController.setUserName(nameField.getText());
                viewMediator.updateDailyValues();
            }
        });

        namePanel.add(nameField);
        namePanel.add(nameButton);


        return namePanel;
    }

    public JPanel createWeightEntryPanel() {
        JPanel weightPanel = new JPanel();

        JTextField weightField = new JTextField();
        weightField.setText("Enter Your Weight");
        weightField.setPreferredSize(new Dimension(150,20));

        JButton weightButton = new JButton("Submit");
        weightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dietController.setPersonalInfo(Double.parseDouble(weightField.getText()), dietController.getCurrentDate());
                viewMediator.updateDailyValues();
            }
        });

        weightPanel.add(weightField);
        weightPanel.add(weightButton);
        return weightPanel;
    }

    public JPanel createDailyGoalsPanel() {
        JPanel goalsPanel = new JPanel();

        JButton goalsButton = new JButton("Set Daily Goals");

        goalsPanel.add(goalsButton);
        return goalsPanel;
    }
}
