package Controllers;

import DatabaseRelatedClasses.Database;
import ServerClasses.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable
{
    @FXML public TextField nickname_tf;
    @FXML public Button newGame_btn, settings_btn, credits_btn, howToPlay_btn, exit_btn;
    public static Player player;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    @FXML
    private void newGameClicked (ActionEvent e) throws Exception
    {
        System.out.println(nickname_tf.getId());
        if (nickname_tf.getText().equals(""))
        {
            nickname_tf.setStyle("-fx-background-color: black; -fx-text-inner-color: white;");
            nickname_tf.setPromptText("Enter a Nickname!");
        }
        else
        {
            newGame_btn.setDisable(true);
            settings_btn.setDisable(true);
            credits_btn.setDisable(true);
            howToPlay_btn.setDisable(true);
            exit_btn.setDisable(true);
            System.out.println(Database.connect());

            Socket socket = new Socket("18.185.120.197", 2641);
            System.out.println("Connected to the server");



            String query = "INSERT INTO player (nickname, color, score, num_of_hackers, num_of_countries, num_of_wins, num_of_losses, num_of_bonus_cards, num_of_bonus_hackers, is_online)"
                    +"VALUES ('"+nickname_tf.getText()+"', 'black', '0', '0', '0', '0', '0', '0', '0', '1')";
            Database.stmt = Database.conn.createStatement();
            Database.stmt.executeUpdate(query);
            query = "SELECT * FROM player WHERE  nickname='"+nickname_tf.getText()+"'";
            Database.stmt = Database.conn.createStatement();
            ResultSet rs = Database.stmt.executeQuery(query);
            rs.next();
            NewGameScene newGameScene = new NewGameScene();
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
