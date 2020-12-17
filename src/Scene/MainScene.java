package Scene;

import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainScene
{
    Parent root;
    Scene scene;

    public MainScene() throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("/Scene/MainScene.fxml"));
        scene = new Scene(root);
        Main.stage.setScene(scene);
    }
}
