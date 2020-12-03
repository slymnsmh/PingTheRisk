import DatabaseRelatedClasses.Database;
import DatabaseRelatedClasses.Lobby;
import DatabaseRelatedClasses.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class JoinGameSceneController implements Initializable
{

    @FXML private AnchorPane commonUI;
    @FXML private Text gameID_txt;
    @FXML private TextField gameID_tf;
    @FXML private Button go_btn;
    @FXML private Button back_btn;

    @FXML
    private void goClicked (ActionEvent e) throws Exception
    {

    }

    @FXML
    private void backClicked (ActionEvent e) throws Exception
    {
        changeScene("Scene/NewGameScene.fxml");
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