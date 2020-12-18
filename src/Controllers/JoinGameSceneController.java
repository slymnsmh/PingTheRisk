package Controllers;

import ServerClasses.Lobby;
import Scene.JoinGameScene;
import Scene.LobbyScene;
import Scene.NewGameScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class JoinGameSceneController implements Initializable
{
    @FXML private Text situation_txt;
    @FXML private AnchorPane main_pane;
    @FXML private Button go_btn;
    @FXML private Button back_btn;
    @FXML private TextField gameID_tf;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private ByteArrayInputStream inputByte = null;
    public static String playerId;
    public static String lobbyId;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        playerId = JoinGameScene.playerId;
    }

    @FXML
    private void goClicked (ActionEvent e) throws Exception
    {
        if (gameID_tf.getText().equals(""))
        {
            gameID_tf.setStyle("-fx-background-color: black; -fx-text-inner-color: white;");
            gameID_tf.setPromptText("Enter a Game ID!");
        }
        else
        {
            lobbyId = gameID_tf.getText();
            main_pane.setCursor(Cursor.WAIT);
            go_btn.setDisable(true);
            back_btn.setDisable(true);
            String sendInfo = "join_game:" + gameID_tf.getText() + ":" + playerId;
            try {
                socket = new Socket("18.185.120.197", 2641);
                System.out.println("Connected to the server");
                input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                output = new DataOutputStream(socket.getOutputStream());
            } catch (Exception ex) {
                System.out.println("There is a problem while connecting the server.");
                System.out.println(ex);
            }
            output.writeUTF(sendInfo);
            String response = input.readUTF();
            if (response.equals("+ok+")) {
                LobbySceneController.ifJoined();
                LobbyScene scene = new LobbyScene(playerId, lobbyId);
            }
            else if (response.equals("+invalid_lobby_id+"))
            {
                situation_txt.setText("Invalid Lobby ID!");
                main_pane.setCursor(Cursor.DEFAULT);
                go_btn.setDisable(false);
                back_btn.setDisable(false);
            }
        }
    }

    @FXML
    private void backClicked (ActionEvent e) throws Exception
    {
        NewGameScene newGameScene = new NewGameScene(playerId);
    }
}