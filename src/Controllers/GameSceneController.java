package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable
{
    @FXML public Pane map_pane;
    @FXML public ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12, img13, img14, img15, img16,
            img17, img18, img19, img20, img21, img22, img23, img24, img25, img26, img27, img28, img29, img30, img31, img32, img33, img34,
            img35;
    @FXML public Label num1_lbl, num2_lbl, num3_lbl, num4_lbl, num5_lbl, num6_lbl, num7_lbl, num8_lbl, num9_lbl, num10_lbl, num11_lbl, num12_lbl,
            num13_lbl, num14_lbl, num15_lbl, num16_lbl, num17_lbl, num18_lbl, num19_lbl, num20_lbl, num21_lbl, num22_lbl, num23_lbl,
            num24_lbl, num25_lbl, num26_lbl, num27_lbl, num29_lbl, num30_lbl, num31_lbl, num32_lbl, num33_lbl, num34_lbl, num35_lbl;
    @FXML public Label p1Nick_lbl, p2Nick_lbl, p3Nick_lbl, p4Nick_lbl;
    @FXML public GridPane settings_grid;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private ByteArrayInputStream inputByte = null;
    public static String playerId;
    public static String lobbyId;

    @Override
    public void initialize( URL location, ResourceBundle resources )
    {
        playerId = LobbySceneController.playerId;
        lobbyId = LobbySceneController.lobbyId;
        for (int i = 0; i < map_pane.getChildren().size(); i++)
        {
            map_pane.getChildren().get(i).setPickOnBounds(false);
        }
        System.out.println("GAME SCENE AÇILDI");
        String sendInfo = "get_game_info:" + playerId + ":" +lobbyId;
        try {
            socket = new Socket("18.185.120.197", 2641);
            System.out.println("Connected to the server");
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            System.out.println("abc");
            output = new DataOutputStream(socket.getOutputStream());
            System.out.println("def");
            output.writeUTF(sendInfo);
            System.out.println("jkl");
        } catch (Exception ex) {
            System.out.println("There is a problem while connecting the server.");
            System.out.println(ex);
        }
    }

    @FXML
    public void regionClicked(MouseEvent e)
    {
        ImageView x = (ImageView)e.getSource();
        System.out.println(x.getId());
    }

    @FXML
    public void howToPlayClicked()
    {
    }

    @FXML
    public void settingsClicked(MouseEvent e)
    {
        if (settings_grid.isVisible() == true)
            settings_grid.setVisible(false);
        else
            settings_grid.setVisible(true);
    }

    @FXML
    public void cardsClicked()
    {

    }
}