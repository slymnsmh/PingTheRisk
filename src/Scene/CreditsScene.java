package Scene;

import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class CreditsScene
{
    public CreditsScene() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/Scene/CreditsScene.fxml"));
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }
}
