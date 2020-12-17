package Scene;

import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class LobbyScene
{
    public LobbyScene() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/Scene/LobbyScene.fxml"));
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }
}
