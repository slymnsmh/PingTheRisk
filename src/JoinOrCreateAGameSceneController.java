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

public class JoinOrCreateAGameSceneController implements Initializable
{

    @FXML private AnchorPane commonUI;
    @FXML private Text gameID_txt;
    @FXML private TextField gameID_tf;
    @FXML private Button go_btn;
    @FXML private Button back_btn;
    public static Lobby lobby;

    @FXML
    private void goClicked (ActionEvent e) throws Exception
    {
        System.out.println(Database.connect());
        Player lobbyHost = MainSceneController.player;
        int randomID = generateLobbyId();
        while (randomID == -1)
        {
            randomID = generateLobbyId();
        }
        String query = "INSERT INTO lobby (id, host_id, num_of_players, player_IDs, player_colors)"
                +"VALUES ( '"+randomID+"','"+lobbyHost.getId()+"', '1', '"+lobbyHost.getId()+"', 'red')";
        Database.stmt = Database.conn.createStatement();
        if (Database.stmt != null)
        {
            Database.stmt.executeUpdate(query);
        }
        lobby = new Lobby(randomID, lobbyHost.getId(), 1, new int[]{lobbyHost.getId()}, new String[]{"red"});
        changeScene("Scene/LobbyScene.fxml");
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

    public int generateLobbyId() throws SQLException
    {
        String query = "SELECT * FROM lobby";
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        int randomID = 10000 + new Random().nextInt(99999);
        boolean isThere = false;
        while (rs.next())
        {
            if (rs.getInt("id") == randomID)
            {
                isThere = true;
                break;
            }
        }
        if (isThere)
            return -1;
        return randomID;
    }
}
