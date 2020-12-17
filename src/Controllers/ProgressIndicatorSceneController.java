package Controllers;

import ServerClasses.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgressIndicatorSceneController implements Initializable
{
    @FXML static ProgressIndicator progressIndicator;
    @FXML Pane pane;
    @FXML AnchorPane main_pane;
    static Player player;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }



    private static void changeScene(String filePath) throws IOException
    {
        Parent newGameMenuParent = FXMLLoader.load(ProgressIndicatorSceneController.class.getResource(filePath));
        Scene newGameMenuScene = new Scene(newGameMenuParent);
        Main.Main.stage.setScene(newGameMenuScene);
        Main.Main.stage.show();
    }
}
