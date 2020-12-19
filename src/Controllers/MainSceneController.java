package Controllers;

import ServerClasses.Player;
import com.sun.glass.ui.ClipboardAssistance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable
{
    @FXML private AnchorPane main_pane;
    @FXML public TextField nickname_tf;
    @FXML public Button newGame_btn, settings_btn, credits_btn, howToPlay_btn, exit_btn;
    public static Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    @FXML
    private void newGameClicked (ActionEvent e) throws Exception
    {
        if (nickname_tf.getText().equals(""))
        {
            nickname_tf.setStyle("-fx-background-color: black; -fx-text-inner-color: white;");
            nickname_tf.setPromptText("Enter a Nickname!");
        }
        else
        {
            main_pane.setCursor(Cursor.WAIT);
            nickname_tf.setDisable(true);
            newGame_btn.setDisable(true);
            settings_btn.setDisable(true);
            credits_btn.setDisable(true);
            howToPlay_btn.setDisable(true);
            exit_btn.setDisable(true);
            String sendInfo = "create_player:" + nickname_tf.getText();
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

            String playerId = input.readUTF();

            if (input.readUTF().equals("+ok+")) {
                NewGameScene scene = new NewGameScene(playerId);
            }
        }
    }

    @FXML
    private void settingsClicked (ActionEvent e) throws Exception
    {
        SettingScene settingScene = new SettingScene();
    }

    @FXML
    private void creditsClicked (ActionEvent e) throws Exception
    {
        CreditsScene creditsScene = new CreditsScene();
    }

    @FXML
    private void howToPlayClicked (ActionEvent e) throws Exception
    {
        HowToPlayScene howToPlayScene = new HowToPlayScene();
    }

    @FXML
    private void exitClicked (ActionEvent e) throws Exception
    {
        System.exit(0);
    }

    /*private void changeScene(String filePath) throws IOException
    {
        Parent newGameMenuParent = FXMLLoader.load(getClass().getResource(filePath));
        Scene newGameMenuScene = new Scene(newGameMenuParent);
        Main.stage.setScene(newGameMenuScene);
        Main.stage.show();
        CommonUIController.isSoundOn = isSoundOn;
    }*/
}
