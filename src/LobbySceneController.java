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

public class LobbySceneController implements Initializable
{
    @FXML private Text lobbyIdLabel_txt, lobbyId_txt;
    @FXML private ImageView p1host_img, p2host_img, p3host_img, p4host_img, p5host_img, p6host_img, p7host_img, p8host_img;
    @FXML private Text player1Nickname_txt, player2Nickname_txt, player3Nickname_txt, player4Nickname_txt, player5Nickname_txt, player6Nickname_txt, player7Nickname_txt, player8Nickname_txt;
    @FXML private GridPane players_grid;
    @FXML private Text players_txt, player1_txt, player2_txt, player3_txt, player4_txt, player5_txt, player6_txt, player7_txt, player8_txt;
    @FXML private AnchorPane commonUI;
    private String[] nicknames;
    private Lobby lobby;
    ResultSet rs;
    ArrayList<Integer> playerIds = new ArrayList<Integer>();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        lobby = NewGameSceneController.lobby;
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

        try
        {
            getNicknames();
            showHost();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
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
        if (playerIds.get(0) !=  null)
            player1Nickname_txt.setText(String.valueOf(playerIds.get(0)));
    }

    public void showHost() throws SQLException
    {
        p1host_img.setVisible(true);
    }
}
