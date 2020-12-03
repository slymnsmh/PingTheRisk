import DatabaseRelatedClasses.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LobbySceneController implements Initializable
{
    @FXML private Text player1Nickname_txt, player2Nickname_txt, player3Nickname_txt, player4Nickname_txt, player5Nickname_txt, player6Nickname_txt, player7Nickname_txt, player8Nickname_txt;
    @FXML private GridPane players_grid;
    @FXML private Text players_txt, player1_txt, player2_txt, player3_txt, player4_txt, player5_txt, player6_txt, player7_txt, player8_txt;
    @FXML private AnchorPane commonUI;
    private String[] nicknames;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        players_grid.setGridLinesVisible(true);
        try
        {
            getNicknames();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }

    public void getNicknames() throws SQLException
    {
        int[] playerIds = JoinOrCreateAGameSceneController.lobby.getPlayerIds();
        String[] playerNicknames = new String[8];
        for (int i = 0; i < playerIds.length; i++)
        {
            String query = "SELECT * from player WHERE id='"+playerIds[i]+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rs = Database.stmt.executeQuery(query);
            rs.next();
            playerNicknames[i] = rs.getString("nickname");
        }
        player1Nickname_txt.setText(String.valueOf(playerNicknames[0]));
    }
}
