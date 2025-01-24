/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygui;

import classes.DatabaseLayer;
import classes.Server;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    private Button startButton;
    @FXML
    private Button closeButton;
    @FXML
    private BarChart<String, Number> barChart;
    private Server server;

    /**
     * Initializes the controller class.
     */
@Override
public void initialize(URL url, ResourceBundle rb) {
    System.out.println("reached controller");

    initializeServer();
    configureBarChart();
    initializeData();
    startDynamicUpdates();
}

private void initializeServer() {
    server = new Server();
}

private void configureBarChart() {
    NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
    yAxis.setAutoRanging(false);
    yAxis.setLowerBound(0);
    yAxis.setUpperBound(10); 
    yAxis.setTickUnit(1);
    xAxis.getStyleClass().add("chart-category-axis");
    yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
        @Override
        public String toString(Number object) {
            return String.format("%d", object.intValue());
        }
    });
}

private void initializeData() {
    XYChart.Series<String, Number> data = createInitialData();
    barChart.getData().add(data);
}

private XYChart.Series<String, Number> createInitialData() {
    XYChart.Series<String, Number> data = new XYChart.Series<>();
    
    data.setName("Users Status");

    try {
        int onlineCount = DatabaseLayer.getOnlineCount();
        int availableCount = DatabaseLayer.getAvailableCount();
        int offlineCount = DatabaseLayer.getOfflineCount();

        data.getData().add(new XYChart.Data<>("Online", onlineCount));
        data.getData().add(new XYChart.Data<>("Available to play", availableCount));
        data.getData().add(new XYChart.Data<>("Offline", offlineCount));
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return data;
}

private void startDynamicUpdates() {
    XYChart.Series<String, Number> data = barChart.getData().get(0);

    new Thread(() -> {
        while (true) {
            try {
                int onlineCount = DatabaseLayer.getOnlineCount();
                int availableCount = DatabaseLayer.getAvailableCount();
                int offlineCount = DatabaseLayer.getOfflineCount();

                javafx.application.Platform.runLater(() -> {
                    data.getData().get(0).setYValue(onlineCount);
                    data.getData().get(1).setYValue(availableCount);
                    data.getData().get(2).setYValue(offlineCount);
                });
                Thread.sleep(1000);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
}



    @FXML
    private void onClickStart(ActionEvent event) {
        System.out.println("Start button clicked");
        new Thread(() -> {
            System.out.println("Starting the server...");
            server.startServer();
        }).start();
    }

    @FXML
    private void onClickClose(ActionEvent event) {
        server.stopServer();
    }

}
