package Controllers;

import Scene.MainScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HowToPlayController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void backClicked () throws IOException {
        MainScene mainScene = new MainScene();
    }
}