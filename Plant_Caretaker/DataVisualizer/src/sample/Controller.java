package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {

    @FXML
    Button button1;
    @FXML
    Button button2;
    @FXML
    Button button3;
    @FXML
    Canvas canvas;
    @FXML
    VBox VBox;

    private int pointPlacement = 10;
    private int dataPlacementIncrement = 1;

    private arduinoReader DataReader = new arduinoReader();

    private ArrayList<Integer> dataFromFile = new ArrayList<Integer>();
    LineChart<Number, Number> lineChart;

    private String dataRead = "StartingString";
    private int dataReadInt;

    private boolean circlesDrawn = false;
    private boolean lineChartDrawn = false;


    public void initialize() {
        DataReader.init();

        //
        //   while (dataRead = DataReader.readLine() && dataRead != null) {
        while (dataRead != null) {
            dataRead = DataReader.readLine();
            if (dataRead == null) {
                break;

            } else {
                System.out.println(" Data from file: " + dataRead);
                dataReadInt = Integer.parseInt(dataRead);
                dataFromFile.add(dataReadInt);

                System.out.println(" Printing array " + dataFromFile);
            }
        }
        //  System.out.println(" Data from file: " + dataFromFile);
    }

    public boolean refresh() {
        DataReader.init();
        dataFromFile.removeAll(dataFromFile);
        dataRead = "StartingString";

        while (dataRead != null) {
            dataRead = DataReader.readLine();
            if (dataRead == null) {
                break;

            } else {
                System.out.println("Data Refresh Test " + dataRead);

                System.out.println(" Data from file: " + dataRead);

                dataReadInt = Integer.valueOf(dataRead);
                dataFromFile.add(dataReadInt);

                System.out.println(" Printing array " + dataFromFile);
            }
        }
        if (circlesDrawn) {
            dataCircles();
        }
        if (lineChartDrawn) {
            dataLineChart();
        }
        return true;
    }

    // Button 1 refreshes the data.
    public void handleButton1Click() {
        refresh();
    }

    // Button 2 displays the data as points
    public void dataCircles() {

        System.out.println("Button2 Clicked");
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        dataPlacementIncrement = 0;

        for (int i = 0; i < dataFromFile.size(); i++) {

            canvas.getGraphicsContext2D().setFill(Color.BLUE);
            canvas.getGraphicsContext2D().fillOval(pointPlacement * dataPlacementIncrement, dataFromFile.get(i), 10, 10);
            canvas.getGraphicsContext2D().strokeText("" + dataFromFile.get(i), pointPlacement * dataPlacementIncrement +1,dataFromFile.get(i));

            dataPlacementIncrement +=2;
            System.out.println( "Data placement" + dataPlacementIncrement);
        }
        circlesDrawn = true;

        if (lineChartDrawn) {

            VBox.getChildren().remove(lineChart);
            lineChartDrawn = false;
        }
    }

    // Button 3 displays the loaded data as text
    public void dataLineChart() {

        if (lineChartDrawn) {
            VBox.getChildren().remove(lineChart);
            lineChartDrawn = false;
        }

        System.out.println("Button1 Clicked");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("voltage");
        yAxis.setLabel("TimeInSeconds");

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("ButtonVoltage");

        XYChart.Series chart = new XYChart.Series();

        for (int i = 0; i < dataFromFile.size() - 1; i++) {
            chart.getData().add(new XYChart.Data<Number, Number>(i, dataFromFile.get(i)));
        }
        lineChart.getData().add(chart);

        VBox.getChildren().add(lineChart);
        lineChartDrawn = true;

        if(circlesDrawn) {

            canvas.getGraphicsContext2D().setFill(Color.WHITE);
            canvas.getGraphicsContext2D().fillRect(0,0,canvas.getWidth(),canvas.getHeight());
            circlesDrawn = false;
        }

    }
}
