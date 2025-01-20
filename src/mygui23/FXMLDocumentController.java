/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygui23;

import classes.DatabaseLayer;
import mygui.*;
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
    CategoryAxis xAxis;
    NumberAxis yAxis;

    /**
     * Initializes the controller class.
     */
@Override
public void initialize(URL url, ResourceBundle rb) {
    System.out.println("reached controller");
    server = new Server();
    NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
    yAxis.setAutoRanging(false); 
    yAxis.setLowerBound(0); 
    yAxis.setUpperBound(10); 
    yAxis.setTickUnit(1); 
    yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
        @Override
        public String toString(Number object) {
            return String.format("%d", object.intValue());
        }
    });
    final int[] onlineCount = new int[1];
    final int[] availableCount = new int[1];
    final int[] offlineCount = new int[1];

    try {
        onlineCount[0] = DatabaseLayer.getOnlineCount();
        availableCount[0] = DatabaseLayer.getAvailableCount();
        offlineCount[0] = DatabaseLayer.getOfflineCount();
    } catch (SQLException sQLException) {
        sQLException.printStackTrace();
    }

    XYChart.Series<String, Number> data = new XYChart.Series<>();
    data.setName("Users Status");
    data.getData().add(new XYChart.Data<>("Online", onlineCount[0]));
    data.getData().add(new XYChart.Data<>("Available to play", availableCount[0]));
    data.getData().add(new XYChart.Data<>("Offline", offlineCount[0]));

    barChart.getData().add(data);

    new Thread(() -> {
        while (true) {
            try {
                onlineCount[0] = DatabaseLayer.getOnlineCount();
                availableCount[0] = DatabaseLayer.getAvailableCount();
                offlineCount[0] = DatabaseLayer.getOfflineCount();
                javafx.application.Platform.runLater(() -> {
                    data.getData().get(0).setYValue(onlineCount[0]);
                    data.getData().get(1).setYValue(availableCount[0]);
                    data.getData().get(2).setYValue(offlineCount[0]);
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
