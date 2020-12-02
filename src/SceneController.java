import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable
{
    @FXML ImageView sound_img;
    @FXML TextField nickname_tf;
    @FXML Button newGame_btn, settings_btn, credits_btn, howToPlay_btn, exit_btn;
    private boolean isSoundOn = true;

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
            Parent newGameMenuParent = FXMLLoader.load(getClass().getResource("Scene/NewGameScene.fxml"));
            Scene newGameMenuScene = new Scene(newGameMenuParent);
            Main.stage.setScene(newGameMenuScene);
            Main.stage.show();
        }
    }

    @FXML
    private void settingsClicked (ActionEvent e) throws Exception
    {
    }

    @FXML
    private void creditsClicked (ActionEvent e) throws Exception
    {
    }

    @FXML
    private void howToPlayClicked (ActionEvent e) throws Exception
    {
    }

    @FXML
    private void exitClicked (ActionEvent e) throws Exception
    {
    }

    @FXML
    private void toggleSound (MouseEvent e)
    {
        System.out.println(isSoundOn);
        Image soundOff = new Image(getClass().getResourceAsStream("MainMenuObjects/voiceOff.png"));
        Image soundOn = new Image(getClass().getResourceAsStream("MainMenuObjects/voiceOn.png"));
        if (isSoundOn)
        {
            sound_img.setImage(soundOff);
            isSoundOn = false;
        }
        else
        {
            sound_img.setImage(soundOn);
            isSoundOn = true;
        }
        System.out.println(isSoundOn);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }
}
