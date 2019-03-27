package view;

import model.DietLog;

import javax.swing.*;
import java.awt.*;

public class BarGraph extends JPanel {

    private DietLog dietLog;
    private int fat = 150;
    private int carbs = 50;
    private int protein = 100;
    private int[] inputData = {0, 50, 100};

    int WIDTH = 275;
    int HEIGHT = 725;

    public BarGraph(DietLog dietLog) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.dietLog = dietLog;


        update(dietLog);
    }


    public void update(DietLog dietLog) {
        fat = (int)dietLog.getFatPerc();
        carbs = (int)dietLog.getCarbPerc();
        protein = (int)dietLog.getProteinPerc();
        updateUI();
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        /*SPACE_BETWEEN_BARS = 10, SPACE_ON_TOP_BOTTOM = 25;*/

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        drawBar(g, Color.RED, fat, 0);
        drawBar(g, Color.GREEN, carbs, 1);
        drawBar(g, Color.BLUE, protein, 2 );

    }

    private void drawBar(Graphics g, Color color, int value, int index) {
        g.setColor(color);

        int barWidth = (WIDTH/3) - 10;
        int x = index * (WIDTH/3) + 5;
        int barHeight = HEIGHT * value/100;
        int y = HEIGHT - barHeight;
        g.fillRect(x, y, barWidth, barHeight);
    }




}
