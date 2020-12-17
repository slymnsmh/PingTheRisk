package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CommonUIController implements Initializable
{
    @FXML
    public ImageView background_img;
    @FXML
    public ImageView sound_img;
    @FXML
    public Parent commonUI;
    public static boolean isSoundOn = true;

    @Override
    public void initialize( URL location, ResourceBundle resources )
    {
    }

    public void setSoundImage()
    {
        Image soundOff = new Image(MainSceneController.class.getResourceAsStream("/MainMenuObjects/voiceOff.png"));
        Image soundOn = new Image(MainSceneController.class.getResourceAsStream("/MainMenuObjects/voiceOn.png"));
        if ( isSoundOn )
            sound_img.setImage(soundOff);
        else
            sound_img.setImage(soundOn);
    }

    @FXML
    public void toggleSound()
    {
        Image soundOff = new Image(MainSceneController.class.getResourceAsStream("/MainMenuObjects/voiceOff.png"));
        Image soundOn = new Image(MainSceneController.class.getResourceAsStream("/MainMenuObjects/voiceOn.png"));
        if ( isSoundOn )
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
}
