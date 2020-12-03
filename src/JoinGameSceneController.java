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

    @FXML private Text situation_txt;
    @FXML private AnchorPane commonUI;
    @FXML private Text gameID_txt;
    @FXML private TextField gameID_tf;
    @FXML private Button go_btn;
    @FXML private Button back_btn;
    public static Lobby lobby;

    @FXML
    private void goClicked (ActionEvent e) throws Exception
    {
        String query = "SELECT * from lobby WHERE id='"+gameID_tf.getText()+"'";
        Database.connect();
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        if (rs.next())
        {
            lobby = new Lobby(Integer.parseInt(gameID_tf.getText()), rs.getInt("host_id"), rs.getInt("num_of_players") + 1, rs.getString("player_IDs"), new String[]{"red"});
            query = "UPDATE lobby set num_of_players = num_of_players + 1, player_IDs = concat(player_IDs, ',"+MainSceneController.player.getId()+"') WHERE id = '"+gameID_tf.getText()+"'";
            Database.stmt = Database.conn.createStatement();
            Database.stmt.executeUpdate(query);
            LobbySceneController.ifJoined();
            changeScene("Scene/LobbyScene.fxml");
        }
        else
        {
            situation_txt.setText("Invalid Lobby ID!");
        }
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