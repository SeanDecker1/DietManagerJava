package view;

import controller.DietController;

import javax.swing.*;

public class DietManagerTabbedPane extends JTabbedPane {

    private DietController dietController;
    private ViewMediator viewMediator;

    public DietManagerTabbedPane(DietController dietController, ViewMediator viewMediator) {

        this.dietController = dietController;
        this.viewMediator = viewMediator;

        UserPanel userPanel = new UserPanel(dietController, viewMediator);
        FoodPanel foodPanel = new FoodPanel(dietController, viewMediator);
        ExercisePanel exercisePanel = new ExercisePanel(dietController, viewMediator);
        LogPanel logPanel = new LogPanel(dietController, viewMediator);

        addTab("User", userPanel);
        addTab("Food", foodPanel);
        addTab("Exercise", exercisePanel);
        addTab("Log", logPanel);
    }
}
