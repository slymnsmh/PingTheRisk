package Controllers;

import DatabaseRelatedClasses.Database;
import Main.Main;
import ServerClasses.Lobby;
import ServerClasses.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class NewGameSceneController implements Initializable
{
    public static Lobby lobby;
    @FXML private AnchorPane main_pane;
    @FXML private Button joinAGame_btn;
    @FXML private Button createAGame_btn;
    @FXML private Button mainMenu_btn;
    Socket socket = null;
    DataInputStream input = null;
    DataOutputStream output = null;
    ByteArrayInputStream inputByte = null;
    public String playerId;
    public static String lobbyId;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        playerId = NewGameScene.playerId;
    }

    @FXML
    private void joinAGameClicked (ActionEvent e) throws Exception
    {
        JoinGameScene joinGameScene = new JoinGameScene(playerId);
    }

    @FXML
    private void createAGameClicked (ActionEvent e) throws Exception
    {
        Main.stage.getScene().setCursor(Cursor.WAIT);
        joinAGame_btn.setDisable(true);
        createAGame_btn.setDisable(true);
        mainMenu_btn.setDisable(true);
        String sendInfo = "create_lobby:" + playerId;
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

        lobbyId = input.readUTF();
        if (input.readUTF().equals("+ok+")) {
            LobbySceneController.ifCreated();
            LobbyScene lobbyScene = new LobbyScene();
        }
    }

    @FXML
    private void mainMenuClicked (ActionEvent e) throws Exception
    {
        MainScene mainScene = new MainScene();
    }

    /*private void changeScene(String filePath) throws IOException
    {
        Parent newGameMenuParent = FXMLLoader.load(getClass().getResource(filePath));
        Scene newGameMenuScene = new Scene(newGameMenuParent);
        Main.Main.stage.setScene(newGameMenuScene);
        Main.Main.stage.show();
    }*/
}
