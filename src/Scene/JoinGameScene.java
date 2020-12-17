package Scene;

import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class JoinGameScene
{
    public static String playerId;
    public JoinGameScene(String playerId) throws IOException
    {
        this.playerId = playerId;
        Parent root = FXMLLoader.load(getClass().getResource("/Scene/JoinGameScene.fxml"));
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }
}
