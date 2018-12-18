package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;

public class Main extends Application {
    public static void main(String[] args) throws ParserConfigurationException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group, 600, 400);

        Parent parent = FXMLLoader.load(getClass().getResource("SvgTo.fxml"));
        group.getChildren().addAll(parent);
        primaryStage.setTitle("SvgToGcode version 0.1");
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
