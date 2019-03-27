package view;

import controller.DietController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DietManagerFrame extends JFrame {

    private DietController dietController;
    private ViewMediator viewMediator;

    private DietManagerTabbedPane tabbedPane;
    private DailyValuesPanel dailyValuesPanel;
    private ChartsPanel chartsPanel;

    public DietManagerFrame(String title, DietController dietController, ViewMediator viewMediator) {
        super(title);

        this.dietController = dietController;
        this.viewMediator = viewMediator;

        setSize(900,800);
        setLayout(new GridLayout(1,3));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dietController.writeCSV();
                System.exit(0);
            }
        });

        tabbedPane = new DietManagerTabbedPane(dietController, viewMediator);
        dailyValuesPanel = new DailyValuesPanel(dietController, viewMediator);
        chartsPanel = new ChartsPanel(dietController, viewMediator);

        add(tabbedPane);

        add(dailyValuesPanel);
        viewMediator.setDailyValuesPanel(dailyValuesPanel);

        add(chartsPanel);
        viewMediator.setChartsPanel(chartsPanel);

        setVisible(true);
    }

    public void repaint() {
        dailyValuesPanel.repaint();
        super.repaint();
    }
}
