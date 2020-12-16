package Controllers;

import DatabaseRelatedClasses.Database;
import DatabaseRelatedClasses.Lobby;
import Scene.LobbyScene;
import Scene.NewGameScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class JoinGameSceneController implements Initializable
{

    @FXML private Text situation_txt;
    @FXML private TextField gameID_tf;
    public static Lobby lobby;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    @FXML
    private void goClicked (ActionEvent e) throws Exception
    {
        String query = "SELECT * from lobby WHERE id='"+gameID_tf.getText()+"'";
        Database.connect();
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        if (rs.next())
        {
            query = "UPDATE lobby set num_of_players = num_of_players + 1, player_IDs = concat(player_IDs, ',"+MainSceneController.player.getId()+"') WHERE id = '"+gameID_tf.getText()+"'";
            Database.stmt = Database.conn.createStatement();
            Database.stmt.executeUpdate(query);

            query = "SELECT * from lobby WHERE id='"+gameID_tf.getText()+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            rs = Database.stmt.executeQuery(query);
            rs.next();

            lobby = new Lobby(rs.getInt("id"), rs.getInt("host_id"), rs.getInt("num_of_players"), rs.getString("player_IDs"), new String[]{"red"});
            System.out.println("LOBBY: ");
            System.out.println(rs.getString("player_IDs"));
            System.out.println(rs.getInt("num_of_players"));
            System.out.println("---------------------------");
            LobbySceneController.ifJoined();
            LobbyScene scene = new LobbyScene();
        }
        else
        {
            situation_txt.setText("Invalid Lobby ID!");
        }
    }

    @FXML
    private void backClicked (ActionEvent e) throws Exception
    {
        NewGameScene newGameScene = new NewGameScene();
    }
}