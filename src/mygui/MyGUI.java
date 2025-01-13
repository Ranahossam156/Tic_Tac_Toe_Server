/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygui;

//import classes.DatabaseLayer;
import classes.Server;

import javafx.application.Application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author a
 */
public class MyGUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new ServerGUI();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        //DatabaseLayer.insert();
        new Thread(()->{
            new Server();
            
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
