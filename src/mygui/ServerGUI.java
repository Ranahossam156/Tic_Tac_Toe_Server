package mygui;

import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ServerGUI extends AnchorPane {

    protected final VBox vBox;
    protected final ImageView imageView;
    protected final Button startBurron;
    protected final Button closeButton;
    protected final CategoryAxis categoryAxis;
    protected final NumberAxis numberAxis;
    protected final BarChart barChart;

    public ServerGUI() {

        vBox = new VBox();
        imageView = new ImageView();
        startBurron = new Button();
        closeButton = new Button();
        categoryAxis = new CategoryAxis();
        numberAxis = new NumberAxis();
        barChart = new BarChart(categoryAxis, numberAxis);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(600.0);
        setPrefWidth(750.0);
        setStyle("-fx-background-color: #8CCDF7;");

        AnchorPane.setBottomAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
        AnchorPane.setTopAnchor(vBox, 0.0);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setLayoutY(1.0);
        vBox.setPrefHeight(600.0);
        vBox.setPrefWidth(750.0);

        imageView.setFitHeight(83.0);
        imageView.setFitWidth(270.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("logo.png").toExternalForm()));

        startBurron.setAlignment(javafx.geometry.Pos.CENTER);
        startBurron.setMnemonicParsing(false);
        startBurron.setPrefHeight(50.0);
        startBurron.setPrefWidth(141.0);
        startBurron.setStyle("-fx-background-color: #ffffff;");
        startBurron.setText("Start");
        startBurron.setTextFill(javafx.scene.paint.Color.valueOf("#8ccdf7"));
        startBurron.setFont(new Font("System Bold", 14.0));
        VBox.setMargin(startBurron, new Insets(30.0, 0.0, 30.0, 0.0));

        closeButton.setMnemonicParsing(false);
        closeButton.setPrefHeight(28.0);
        closeButton.setPrefWidth(141.0);
        closeButton.setStyle("-fx-background-color: #ffffff;");
        closeButton.setText("Close");
        closeButton.setTextFill(javafx.scene.paint.Color.valueOf("#8ccdf7"));
        VBox.setMargin(closeButton, new Insets(0.0, 0.0, 30.0, 0.0));
        closeButton.setFont(new Font("System Bold", 14.0));

        categoryAxis.setLabel("Status");

        numberAxis.setLabel("Number of user");
        numberAxis.setSide(javafx.geometry.Side.LEFT);
        barChart.setPrefHeight(411.0);
        barChart.setPrefWidth(750.0);
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Status");
        categoryAxis.setStyle("-fx-label-fill: white;");
        numberAxis.setStyle("-fx-label-fill: white;");
        

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Users");

        // Create the BarChart
        // BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        // Add data to the BarChart
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Users Status");
         //   data.getNode().setStyle("-fx-text-fill: #ff6347;");
        data.getData().add(new XYChart.Data<>("Online", 50));
        data.getData().add(new XYChart.Data<>("Available to play", 30));
        data.getData().add(new XYChart.Data<>("Offline", 40));
        
        categoryAxis.setStyle("-fx-tick-label-font-size: 14px; -fx-tick-label-font-weight: bold;");
        numberAxis.setStyle("-fx-tick-label-font-size: 14px; -fx-tick-label-font-weight: bold;");
//barChart.lookupAll(".default-color0.chart-bar").forEach(bar -> bar.setStyle("-fx-bar-fill: #8ccdf7;"));
        categoryAxis.setTickLabelFill(javafx.scene.paint.Color.valueOf("#ffffff"));
        numberAxis.setTickLabelFill(javafx.scene.paint.Color.valueOf("#ffffff"));
        barChart.getData().add(data);

        vBox.getChildren().add(imageView);
        vBox.getChildren().add(startBurron);
        vBox.getChildren().add(closeButton);
        vBox.getChildren().add(barChart);
        getChildren().add(vBox);

    }
}
