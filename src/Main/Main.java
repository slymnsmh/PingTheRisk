package Main;

import Scene.GameScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        GameScene mainScene = new GameScene();
        stage.setTitle("Ping The Risk v1.0");

        //stage.setMaximized(true);
        //stage.setFullScreen(true);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/StageObjects/pingTheRisk_logo.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
