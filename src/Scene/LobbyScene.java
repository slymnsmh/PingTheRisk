package Scene;

import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class LobbyScene
{
    public static String playerId;
    public static String lobbyId;
    public LobbyScene(String playerId, String lobbyId) throws IOException
    {
        this.playerId = playerId;
        this.lobbyId = lobbyId;
        Parent root = FXMLLoader.load(getClass().getResource("/Scene/LobbyScene.fxml"));
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }
}
