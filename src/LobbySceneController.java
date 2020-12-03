import DatabaseRelatedClasses.Database;
import DatabaseRelatedClasses.Lobby;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class LobbySceneController implements Initializable
{
    @FXML private Text lobbyIdLabel_txt, lobbyId_txt;
    @FXML private ImageView p1host_img, p2host_img, p3host_img, p4host_img, p5host_img, p6host_img, p7host_img, p8host_img;
    @FXML private Text player1Nickname_txt, player2Nickname_txt, player3Nickname_txt, player4Nickname_txt, player5Nickname_txt, player6Nickname_txt, player7Nickname_txt, player8Nickname_txt;
    @FXML private GridPane players_grid;
    @FXML private Text players_txt, player1_txt, player2_txt, player3_txt, player4_txt, player5_txt, player6_txt, player7_txt, player8_txt;
    @FXML private AnchorPane commonUI;
    private String[] nicknames;
    private static Lobby lobby;
    ResultSet rs;
    ArrayList<Integer> playerIds = new ArrayList<Integer>();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        players_grid.setGridLinesVisible(true);
        lobbyId_txt.setText(String.valueOf(lobby.getId()));
        try
        {
            String query = "SELECT * from lobby WHERE id='"+ lobby.getId()+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            rs = Database.stmt.executeQuery(query);
            rs.next();
        }
        catch (Exception e)
        {
            System.out.println("Database connection problem: " + e.getMessage());
        }
        try {
            showHost();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            getNicknames();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }, 0, 10);
    }

    public static void ifCreated()
    {
        lobby = NewGameSceneController.lobby;
    }

    public static void ifJoined()
    {
        lobby = JoinGameSceneController.lobby;
    }

    public void getNicknames() throws SQLException
    {
        String playerIdsStr = rs.getString("player_IDs");
        System.out.println(playerIdsStr);
        String idStr = "";
        for (int i = 0; i < playerIdsStr.length(); i++)
        {
            if (playerIdsStr.charAt(i) == ',')
            {
                int id = Integer.parseInt(idStr);
                playerIds.add(id);
                idStr = "";
                continue;
            }
            idStr += playerIdsStr.charAt(i);
        }
        int id = Integer.parseInt(idStr);
        playerIds.add(id);
        for (int i = 0; i < playerIds.size(); i++)
            System.out.println(playerIds.get(i));

        if (playerIds.get(0) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(0)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player1Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
        if (playerIds.get(1) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(1)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player2Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
        if (playerIds.get(2) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(2)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player3Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
        if (playerIds.get(3) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(3)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player4Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
        if (playerIds.get(4) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(4)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player5Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
        if (playerIds.get(5) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(5)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player6Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
        if (playerIds.get(6) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(6)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player7Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
        if (playerIds.get(7) !=  null)
        {
            String query = "SELECT * from player WHERE id='"+playerIds.get(7)+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            player8Nickname_txt.setText(rsPlayer.getString("nickname"));
        }
    }

    public void showHost() throws SQLException
    {
        p1host_img.setVisible(true);
    }
}
