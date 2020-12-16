package Controllers;

import DatabaseRelatedClasses.Database;
import DatabaseRelatedClasses.Lobby;
import Managers.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class LobbySceneController implements Initializable
{
    @FXML
    ImageView p2Remove_img, p3Remove_img, p4Remove_img, p5Remove_img, p6Remove_img, p7Remove_img, p8Remove_img;
    @FXML
    private Text lobbyId_txt;
    @FXML
    private ImageView p1host_img;
    @FXML
    private Text player1Nickname_txt, player2Nickname_txt, player3Nickname_txt, player4Nickname_txt, player5Nickname_txt, player6Nickname_txt, player7Nickname_txt, player8Nickname_txt;
    @FXML
    private GridPane players_grid;
    private static Lobby lobby;
    ResultSet rs;

    @Override
    public void initialize( URL location, ResourceBundle resources )
    {
        players_grid.setGridLinesVisible(true);
        lobbyId_txt.setText(String.valueOf(lobby.getId()));
        try
        {
            showHost();
        }
        catch ( SQLException throwables )
        {
            throwables.printStackTrace();
        }
        new Timer().scheduleAtFixedRate(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            System.out.println("ANAN");
                            try
                            {
                                String query = "SELECT * from lobby WHERE id='" + lobby.getId() + "'";
                                Database.connect();
                                Database.stmt = Database.conn.createStatement();
                                rs = Database.stmt.executeQuery(query);
                                rs.next();
                                lobby.setPlayerIds(rs.getString("player_IDs"));
                                lobby.setNumOfPlayers(rs.getInt("num_of_players"));
                            }
                            catch ( Exception e )
                            {
                                System.out.println("Database connection problem: " + e.getMessage());
                            }
                            getNicknames();

                        }
                        catch ( SQLException throwables )
                        {
                            throwables.printStackTrace();
                        }
                    }
                }, 0, 1);
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
        ArrayList<String> playerIds = lobby.getPlayerIdsArray();
        ArrayList<String> playerNicknames = new ArrayList<>();
        for ( String j : playerIds )
        {
            String query = "SELECT * from player WHERE id='" + j + "'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            playerNicknames.add(rsPlayer.getString("nickname"));
        }
        if ( playerIds.size() == 1 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText("");
            p2Remove_img.setVisible(false);
            player3Nickname_txt.setText("");
            p3Remove_img.setVisible(false);
            player4Nickname_txt.setText("");
            p4Remove_img.setVisible(false);
            player5Nickname_txt.setText("");
            p5Remove_img.setVisible(false);
            player6Nickname_txt.setText("");
            p6Remove_img.setVisible(false);
            player7Nickname_txt.setText("");
            p7Remove_img.setVisible(false);
            player8Nickname_txt.setText("");
            p8Remove_img.setVisible(false);
        }
        if ( playerIds.size() == 2 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText("");
            p3Remove_img.setVisible(false);
            player4Nickname_txt.setText("");
            p4Remove_img.setVisible(false);
            player5Nickname_txt.setText("");
            p5Remove_img.setVisible(false);
            player6Nickname_txt.setText("");
            p6Remove_img.setVisible(false);
            player7Nickname_txt.setText("");
            p7Remove_img.setVisible(false);
            player8Nickname_txt.setText("");
            p8Remove_img.setVisible(false);
        }
        if ( playerIds.size() == 3 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText("");
            p4Remove_img.setVisible(false);
            player5Nickname_txt.setText("");
            p5Remove_img.setVisible(false);
            player6Nickname_txt.setText("");
            p6Remove_img.setVisible(false);
            player7Nickname_txt.setText("");
            p7Remove_img.setVisible(false);
            player8Nickname_txt.setText("");
            p8Remove_img.setVisible(false);
        }
        if ( playerIds.size() == 4 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText(playerNicknames.get(3));
            p4Remove_img.setVisible(true);
            player5Nickname_txt.setText("");
            p5Remove_img.setVisible(false);
            player6Nickname_txt.setText("");
            p6Remove_img.setVisible(false);
            player7Nickname_txt.setText("");
            p7Remove_img.setVisible(false);
            player8Nickname_txt.setText("");
            p8Remove_img.setVisible(false);
        }
        if ( playerIds.size() == 5 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText(playerNicknames.get(3));
            p4Remove_img.setVisible(true);
            player5Nickname_txt.setText(playerNicknames.get(4));
            p5Remove_img.setVisible(true);
            player6Nickname_txt.setText("");
            p6Remove_img.setVisible(false);
            player7Nickname_txt.setText("");
            p7Remove_img.setVisible(false);
            player8Nickname_txt.setText("");
            p8Remove_img.setVisible(false);
        }
        if ( playerIds.size() == 6 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText(playerNicknames.get(3));
            p4Remove_img.setVisible(true);
            player5Nickname_txt.setText(playerNicknames.get(4));
            p5Remove_img.setVisible(true);
            player6Nickname_txt.setText(playerNicknames.get(5));
            p6Remove_img.setVisible(true);
            player7Nickname_txt.setText("");
            p7Remove_img.setVisible(false);
            player8Nickname_txt.setText("");
            p8Remove_img.setVisible(false);
        }
        if ( playerIds.size() == 7 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText(playerNicknames.get(3));
            p4Remove_img.setVisible(true);
            player5Nickname_txt.setText(playerNicknames.get(4));
            p5Remove_img.setVisible(true);
            player6Nickname_txt.setText(playerNicknames.get(5));
            p6Remove_img.setVisible(true);
            player7Nickname_txt.setText(playerNicknames.get(6));
            p7Remove_img.setVisible(true);
            player8Nickname_txt.setText("");
            p8Remove_img.setVisible(false);
        }
        if ( playerIds.size() == 8 )
        {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText(playerNicknames.get(3));
            p4Remove_img.setVisible(true);
            player5Nickname_txt.setText(playerNicknames.get(4));
            p5Remove_img.setVisible(true);
            player6Nickname_txt.setText(playerNicknames.get(5));
            p6Remove_img.setVisible(true);
            player7Nickname_txt.setText(playerNicknames.get(6));
            p7Remove_img.setVisible(true);
            player8Nickname_txt.setText(playerNicknames.get(7));
            p8Remove_img.setVisible(true);
        }
    }

    @FXML
    public void banClicked( MouseEvent event ) throws SQLException
    {
        //if player is host ekle **********************************************
        ImageView clicked = (ImageView) event.getSource();
        int clickedWhichPlayer = 1;
        for ( int i = 0; i < clicked.getId().length(); i++ )
        {
            if ( clicked.getId().charAt(i) == '2' || clicked.getId().charAt(i) == '3' || clicked.getId().charAt(i) == '4' || clicked.getId().charAt(i) == '5'
                    || clicked.getId().charAt(i) == '6' || clicked.getId().charAt(i) == '7' || clicked.getId().charAt(i) == '8' )
            {
                clickedWhichPlayer = Integer.parseInt(String.valueOf(clicked.getId().charAt(i)));
                break;
            }
        }
        if ( clickedWhichPlayer != 1 )
        {
            ArrayList<String> playerIdsArray = lobby.getPlayerIdsArray();
            System.out.println("PPP");
            for ( String a : playerIdsArray )
            {
                System.out.println(a);
            }
            System.out.println("PPP");
            int clickedPlayerId = Integer.parseInt(playerIdsArray.get(clickedWhichPlayer - 1));
            playerIdsArray.remove(clickedWhichPlayer - 1);

            String newPlayerIds = "";
            for ( int i = 0; i < playerIdsArray.size(); i++ )
            {
                newPlayerIds += playerIdsArray.get(i) + ",";
            }
            newPlayerIds = newPlayerIds.substring(0, newPlayerIds.length() - 1);

            System.out.println(newPlayerIds);

            lobby.setNumOfPlayers(lobby.getNumOfPlayers() - 1);
            lobby.setPlayerIds(newPlayerIds);

            try
            {
                String query = "UPDATE lobby set num_of_players = num_of_players - 1, player_IDs = '" + newPlayerIds + "' WHERE id='" + lobby.getId() + "'";
                PreparedStatement statement = Database.conn.prepareStatement(query);
                statement.execute();
                System.out.println("Silindi: " + clickedPlayerId);
                System.out.println("NumPfPlayers decreased to: " + (lobby.getNumOfPlayers()));
            }
            catch ( SQLException sqlException )
            {
                System.out.println(sqlException.getMessage());
            }
        }
    }

    @FXML
    public void startGameClicked() throws SQLException
    {
        GameManager gameManager = new GameManager(lobby, lobby.getPlayerIdsArray());
    }

    public void showHost() throws SQLException
    {
        p1host_img.setVisible(true);
    }
}
