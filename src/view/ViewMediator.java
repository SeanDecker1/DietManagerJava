package view;

public class ViewMediator {

    private DailyValuesPanel dailyValuesPanel;
    private ChartsPanel chartsPanel;
    private LogPanel logPanel;

    public void setChartsPanel(ChartsPanel chartsPanel) {
        this.chartsPanel = chartsPanel;
    }

    public void setDailyValuesPanel(DailyValuesPanel dailyValuesPanel) {
        this.dailyValuesPanel = dailyValuesPanel;
    }

    public void setLogPanel(LogPanel logPanel) {
        this.logPanel = logPanel;
    }

    public void updateDailyValues() {
        dailyValuesPanel.updateValues();
    }

    public void updateDailyCharts() {
        chartsPanel.updatePanel();
    }

    public void updateLogPanel() {
        logPanel.updatePanel();
    }
}
