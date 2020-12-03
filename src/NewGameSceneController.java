import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGameSceneController implements Initializable
{
    @FXML private AnchorPane main_pane;
    @FXML private AnchorPane commonUI;
    @FXML private Button joinAGame_btn, createAGame_btn, mainMenu_btn;

    @FXML
    private void joinAGameClicked (ActionEvent e) throws Exception
    {
        changeScene("Scene/JoinOrCreateAGameScene.fxml");
    }

    @FXML
    private void createAGameClicked (ActionEvent e) throws Exception
    {
        changeScene("Scene/JoinOrCreateAGameScene.fxml");
    }

    @FXML
    private void mainMenuClicked (ActionEvent e) throws Exception
    {
        changeScene("Scene/MainScene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    private void changeScene(String filePath) throws IOException
    {
        Parent newGameMenuParent = FXMLLoader.load(getClass().getResource(filePath));
        Scene newGameMenuScene = new Scene(newGameMenuParent);
        Main.stage.setScene(newGameMenuScene);
        Main.stage.show();
    }
}
