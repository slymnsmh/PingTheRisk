package Scene;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author slymn
 */
public class Main extends Application
{
    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Ping The Risk v1.0");
        primaryStage.setWidth(966);
        primaryStage.setHeight(568);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("StageObjects/pingTheRisk_logo.png")));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
