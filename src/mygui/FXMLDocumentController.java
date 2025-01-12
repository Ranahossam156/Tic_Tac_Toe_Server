/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author a
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label logo;
    @FXML
    private Button startBurron;
    @FXML
    private Button closeButton;
    @FXML
    private BarChart<?, ?> barChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Status");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Users");

        // Create the BarChart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Add data to the BarChart
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Users Status");
        data.getData().add(new XYChart.Data<>("Online", 50));
        data.getData().add(new XYChart.Data<>("Available to play", 30));
        data.getData().add(new XYChart.Data<>("Offline", 40));

        barChart.getData().add(data);
        // TODO
    }

}
