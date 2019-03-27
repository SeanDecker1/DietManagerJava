package view;

import controller.DietController;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class FoodPanel extends JPanel {

    private DietController dietController;
    private ViewMediator viewMediator;

    private JPanel recipeIngredientsPanel;
    private JPanel addFoodPanel;
    private JPanel addRecipePanel;

    private JTextField foodNameField;
    private JTextField caloriesField;
    private JTextField fatField;
    private JTextField carbsField;
    private JTextField proteinField;
    private JTextField recipeNameField;

    private Set<String> foodList;

    public FoodPanel(DietController dietController, ViewMediator viewMediator) {

        this.dietController = dietController;
        this.viewMediator = viewMediator;

        foodList = dietController.getFoodListKeys();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addFoodPanel = createFoodPanel();
        add(addFoodPanel);

        addRecipePanel = createRecipePanel();
        add(addRecipePanel);
    }

    public JPanel createFoodPanel() {

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new GridLayout(6,1));


        JPanel foodNamePanel = new JPanel();
        foodNamePanel.setLayout(new FlowLayout());
        JLabel foodNameLabel = new JLabel("Food Name");
        foodNameField = new JTextField();
        foodNameField.setText("");
        foodNameField.setPreferredSize(new Dimension(150, 20));
        foodNamePanel.add(foodNameLabel);
        foodNamePanel.add(foodNameField);

        JPanel caloriesPanel = new JPanel();
        caloriesPanel.setLayout(new FlowLayout());
        JLabel caloriesLabel = new JLabel("Calories");
        caloriesField = new JTextField();
        caloriesField.setText("");
        caloriesField.setPreferredSize(new Dimension(150, 20));
        caloriesPanel.add(caloriesLabel);
        caloriesPanel.add(caloriesField);

        JPanel carbsPanel = new JPanel();
        carbsPanel.setLayout(new FlowLayout());
        JLabel carbsLabel = new JLabel("Carbs (g)");
        carbsField = new JTextField();
        carbsField.setText("");
        carbsField.setPreferredSize(new Dimension(150, 20));
        carbsPanel.add(carbsLabel);
        carbsPanel.add(carbsField);

        JPanel proteinPanel = new JPanel();
        proteinPanel.setLayout(new FlowLayout());
        JLabel proteinLabel = new JLabel("Protein (g)");
        proteinField = new JTextField();
        proteinField.setText("");
        proteinField.setPreferredSize(new Dimension(150, 20));
        proteinPanel.add(proteinLabel);
        proteinPanel.add(proteinField);

        JPanel fatPanel = new JPanel();
        fatPanel.setLayout(new FlowLayout());
        JLabel fatLabel = new JLabel("Fat (g)");
        fatField = new JTextField();
        fatField.setText("");
        fatField.setPreferredSize(new Dimension(150, 20));
        fatPanel.add(fatLabel);
        fatPanel.add(fatField);
        

        JButton addFoodButton = new JButton("Add Basic Food");
        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String foodName = foodNameField.getText();
                    dietController.addBasicFood(foodName,
                        Integer.parseInt(caloriesField.getText()),
                        Double.parseDouble(carbsField.getText()),
                        Double.parseDouble(proteinField.getText()),
                        Double.parseDouble(fatField.getText()));
                    resetPanel();
                    viewMediator.updateLogPanel();
                    JOptionPane.showMessageDialog(null,
                            "Food successfully added: " + foodName);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                        "Food failed to add. Please ensure all values are valid.");
                }
            }
        });

        foodPanel.add(foodNamePanel);
        foodPanel.add(caloriesPanel);
        foodPanel.add(carbsPanel);
        foodPanel.add(proteinPanel);
        foodPanel.add(fatPanel);
        foodPanel.add(addFoodButton);
        return foodPanel;
    }

    public JPanel createRecipePanel() {
        JPanel recipePanel = new JPanel();
        recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));;

        JPanel recipeNamePanel = new JPanel();
        recipeNamePanel.setLayout(new FlowLayout());
        JLabel recipeNameLabel = new JLabel("Recipe Name");
        recipeNameField = new JTextField();
        recipeNameField.setText("");
        recipeNameField.setPreferredSize(new Dimension(150, 20));
        recipeNamePanel.add(recipeNameLabel);
        recipeNamePanel.add(recipeNameField);




        recipeIngredientsPanel = new JPanel();
        recipeIngredientsPanel.setLayout(new BoxLayout(recipeIngredientsPanel, BoxLayout.Y_AXIS));
        JScrollPane recipeIngredientsScrollPane = new JScrollPane(recipeIngredientsPanel);
        addIngredientInputPanel();



        JButton recipeButton = new JButton("Add Recipe");
        recipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipeName = recipeNameField.getText();
                try {
                    addRecipe();
                    JOptionPane.showMessageDialog(null,
                            "Recipe successfully added: " + recipeName);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());
                }

            }
        });

        JPanel addItemPanel = new JPanel();
        addItemPanel.setLayout(new FlowLayout());
        JButton addItemButton = new JButton("+");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIngredientInputPanel();
            }
        });
        addItemPanel.add(addItemButton);

        recipePanel.add(recipeNamePanel);
        recipePanel.add(recipeIngredientsScrollPane);
        recipePanel.add(addItemPanel);
        recipePanel.add(recipeButton);
        return recipePanel;
    }

    private JPanel addIngredientInputPanel() {
        JPanel ingredientPanel = new JPanel();
        ingredientPanel.setLayout(new FlowLayout());

        JLabel ingredientLabel = new JLabel("Food");
        JComboBox ingredientComboBox = new JComboBox(foodList.toArray());
        JLabel servingLabel = new JLabel("Servings");
        JTextField servingField = new JTextField();
        servingField.setText("1.0");
        servingField.setPreferredSize(new Dimension(30, 20));
        JButton removeItemButton = new JButton("-");
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipeIngredientsPanel.remove(ingredientPanel);
                recipeIngredientsPanel.updateUI();
            }
        });

        ingredientPanel.add(ingredientLabel);
        ingredientPanel.add(ingredientComboBox);
        ingredientPanel.add(servingLabel);
        ingredientPanel.add(servingField);
        ingredientPanel.add(removeItemButton);

        recipeIngredientsPanel.add(ingredientPanel);
        recipeIngredientsPanel.updateUI();
        return ingredientPanel;
    }

    private void addRecipe() {
        ArrayList<String> recipeList = new ArrayList<String>();
        ArrayList<String> servingList = new ArrayList<String>();
        for (Component panel : recipeIngredientsPanel.getComponents()) {
            JPanel innerPanel = (JPanel)panel;
            recipeList.add(((JComboBox)innerPanel.getComponent(1)).getSelectedItem().toString());
            servingList.add(((JTextField)innerPanel.getComponent(3)).getText());
        }
        dietController.addRecipe(recipeNameField.getText(),recipeList,servingList);
        resetPanel();
        viewMediator.updateLogPanel();
    }

    public void resetPanel() {
        foodList = dietController.getFoodListKeys();

        recipeIngredientsPanel.removeAll();
        foodNameField.setText("");
        recipeNameField.setText("");
        caloriesField.setText("");
        fatField.setText("");
        carbsField.setText("");
        proteinField.setText("");

        addIngredientInputPanel();
        this.updateUI();

    }

}
