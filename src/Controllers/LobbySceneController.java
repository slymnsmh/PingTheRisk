package Controllers;

import Scene.LobbyScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class LobbySceneController implements Initializable {
    @FXML Button refresh_btn;
    @FXML Button startGame_btn;
    @FXML ImageView p2Remove_img, p3Remove_img, p4Remove_img;
    @FXML private Text lobbyId_txt;
    @FXML private ImageView p1host_img;
    @FXML private Text player1Nickname_txt, player2Nickname_txt, player3Nickname_txt, player4Nickname_txt;
    @FXML private GridPane players_grid;
    ResultSet rs;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private ByteArrayInputStream inputByte = null;
    public static String playerId;
    public static String lobbyId;// = 456
    boolean isStop = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerId = LobbyScene.playerId; //123
        players_grid.setGridLinesVisible(true);
        lobbyId_txt.setText(lobbyId);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshClicked();
            }
        }, 0, 5000);
        /*try {
            showHost();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    @FXML
    public void refreshClicked()
    {
        try {
            socket = new Socket("18.185.120.197", 2641);
            System.out.println("Connected to the server");
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            String request = "update_lobby:" + playerId + ":" + lobbyId;
            output.writeUTF(request);
            System.out.println("RESPONSE SENT!!! : " + request);
        } catch (Exception ex) {
            System.out.println("There is a problem while connecting the server.");
            System.out.println(ex);
        }
        ////////////////////////

        String response = "";
        int playerNumber = 0;
        String playerNicknamesStr = "";
        try {
            response = input.readUTF();
            System.out.println("r1: " + response);
            if (response.equals("+upload+")) {
                playerNumber = Integer.parseInt(input.readUTF());
                System.out.println("r2: " + playerNumber);
                playerNicknamesStr += input.readUTF();
                System.out.println("r3: " + playerNicknamesStr);
                getNicknames(playerNumber, playerNicknamesStr);
            }
        } catch (IOException e) {
            System.out.println("No answer from server. Trying again...");
        }
    }

    @FXML
    public void startClicked()
    {
        isStop = true;
    }

    public static void ifCreated() {
        lobbyId = NewGameSceneController.lobbyId;
    }

    public static void ifJoined() {
        lobbyId = JoinGameSceneController.lobbyId;
    }

    public void getNicknames(int playerNumber, String playerNicknamesStr) {
        ArrayList<String> playerNicknames = new ArrayList<>();
        for (int i = 0; i < playerNumber; i++)
        {
            System.out.println("ANAN3");
            int counter = 0;
            while (counter < playerNicknamesStr.length() && playerNicknamesStr.charAt(counter) != ',') {
                counter++;
            }
            playerNicknames.add(playerNicknamesStr.substring(0, counter));
            if (playerNicknamesStr.length() > counter)
                playerNicknamesStr = playerNicknamesStr.substring(counter + 1);
            else
                playerNicknamesStr = "";
            System.out.println(playerNicknames.get(i));
            System.out.println(playerNicknamesStr);
        }
        System.out.println("ANAN4");

        if (playerNumber == 1) {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText("");
            p2Remove_img.setVisible(false);
            player3Nickname_txt.setText("");
            p3Remove_img.setVisible(false);
            player4Nickname_txt.setText("");
            p4Remove_img.setVisible(false);
            System.out.println("ANAN5");
        }
        if (playerNumber == 2) {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText("");
            p3Remove_img.setVisible(false);
            player4Nickname_txt.setText("");
            p4Remove_img.setVisible(false);
            System.out.println("ANAN6");
        }
        if (playerNumber == 3) {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText("");
            p4Remove_img.setVisible(false);
            System.out.println("ANAN7");
        }
        if (playerNumber == 4) {
            player1Nickname_txt.setText(playerNicknames.get(0));
            player2Nickname_txt.setText(playerNicknames.get(1));
            p2Remove_img.setVisible(true);
            player3Nickname_txt.setText(playerNicknames.get(2));
            p3Remove_img.setVisible(true);
            player4Nickname_txt.setText(playerNicknames.get(3));
            p4Remove_img.setVisible(true);
            System.out.println("ANAN8");
        }
    }
    /*@FXML
    public void banClicked(MouseEvent event) throws SQLException {
        //if player is host ekle **********************************************
        ImageView clicked = (ImageView) event.getSource();
        int clickedWhichPlayer = 1;
        for (int i = 0; i < clicked.getId().length(); i++) {
            if (clicked.getId().charAt(i) == '2' || clicked.getId().charAt(i) == '3' || clicked.getId().charAt(i) == '4' || clicked.getId().charAt(i) == '5'
                    || clicked.getId().charAt(i) == '6' || clicked.getId().charAt(i) == '7' || clicked.getId().charAt(i) == '8') {
                clickedWhichPlayer = Integer.parseInt(String.valueOf(clicked.getId().charAt(i)));
                break;
            }
        }
        if (clickedWhichPlayer != 1) {
            ArrayList<String> playerIdsArray = lobby.getPlayerIdsArray();
            System.out.println("PPP");
            for (String a : playerIdsArray) {
                System.out.println(a);
            }
            System.out.println("PPP");
            int clickedPlayerId = Integer.parseInt(playerIdsArray.get(clickedWhichPlayer - 1));
            playerIdsArray.remove(clickedWhichPlayer - 1);

            String newPlayerIds = "";
            for (int i = 0; i < playerIdsArray.size(); i++) {
                newPlayerIds += playerIdsArray.get(i) + ",";
            }
            newPlayerIds = newPlayerIds.substring(0, newPlayerIds.length() - 1);

            System.out.println(newPlayerIds);

            lobby.setNumOfPlayers(lobby.getNumOfPlayers() - 1);
            lobby.setPlayerIds(newPlayerIds);

            try {
                String query = "UPDATE lobby set num_of_players = num_of_players - 1, player_IDs = '" + newPlayerIds + "' WHERE id='" + lobby.getId() + "'";
                PreparedStatement statement = Database.conn.prepareStatement(query);
                statement.execute();
                System.out.println("Silindi: " + clickedPlayerId);
                System.out.println("NumPfPlayers decreased to: " + (lobby.getNumOfPlayers()));
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }
        }
    }

    @FXML
    public void startGameClicked() throws SQLException {
        GameManager gameManager = new GameManager(lobby, lobby.getPlayerIdsArray());
    }

    public void showHost() throws SQLException {
        p1host_img.setVisible(true);
    }*/
}
