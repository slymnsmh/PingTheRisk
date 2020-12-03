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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class NewGameSceneController implements Initializable
{
    @FXML private AnchorPane main_pane;
    @FXML private AnchorPane commonUI;
    @FXML private Button joinAGame_btn, createAGame_btn, mainMenu_btn;
    public static Lobby lobby;

    @FXML
    private void joinAGameClicked (ActionEvent e) throws Exception
    {
        changeScene("Scene/JoinGameScene.fxml");
    }

    @FXML
    private void createAGameClicked (ActionEvent e) throws Exception
    {
        System.out.println(Database.connect());
        Player lobbyHost = MainSceneController.player;
        int randomID = generateLobbyId();
        while (randomID == -1)
        {
            randomID = generateLobbyId();
        }
        System.out.println("RANDOM ID: " + randomID);
        String query = "INSERT INTO lobby (id, host_id, num_of_players, player_IDs, player_colors)"
                +"VALUES ( '"+randomID+"','"+lobbyHost.getId()+"', '1', '"+lobbyHost.getId()+"', 'red')";
        Database.stmt = Database.conn.createStatement();
        if (Database.stmt != null)
        {
            Database.stmt.executeUpdate(query);
        }
        lobby = new Lobby(randomID, lobbyHost.getId(), 1, String.valueOf(lobbyHost.getId()), new String[]{"red"});
        LobbySceneController.ifCreated();
        changeScene("Scene/LobbyScene.fxml");
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

    public int generateLobbyId() throws SQLException
    {
        String query = "SELECT * FROM lobby";
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        int randomID = 100000000 + new Random().nextInt(999999999);
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
