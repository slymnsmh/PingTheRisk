package Scene;

import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GameScene
{
    public GameScene() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/Scene/GameScene.fxml"));
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }
}
