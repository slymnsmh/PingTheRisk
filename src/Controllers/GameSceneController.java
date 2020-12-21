package Controllers;

import Scene.GameScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable
{
    @FXML public Pane map_pane;
    @FXML public ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12, img13, img14, img15, img16,
            img17, img18, img19, img20, img21, img22, img23, img24, img25, img26, img27, img28, img29, img30, img31, img32, img33, img34,
            img35, img36;
    @FXML public Label num1_lbl, num2_lbl, num3_lbl, num4_lbl, num5_lbl, num6_lbl, num7_lbl, num8_lbl, num9_lbl, num10_lbl, num11_lbl, num12_lbl,
            num13_lbl, num14_lbl, num15_lbl, num16_lbl, num17_lbl, num18_lbl, num19_lbl, num20_lbl, num21_lbl, num22_lbl, num23_lbl,
            num24_lbl, num25_lbl, num26_lbl, num27_lbl, num29_lbl, num30_lbl, num31_lbl, num32_lbl, num33_lbl, num34_lbl, num35_lbl, num36_lbl;
    @FXML public Label p1Nick_lbl, p2Nick_lbl, p3Nick_lbl, p4Nick_lbl;
    @FXML public GridPane settings_grid;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private ByteArrayInputStream inputByte = null;
    public static String playerId;
    public static String lobbyId;
    private String numOfPlayers, playerIds, nicknames, total_num_countries, ownerIds, hackerNums;

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
        try {
            socket = new Socket("18.185.120.197", 2641);
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateScene();
    }

    public void updateScene()
    {
        String sendInfo = "get_game_info:" + playerId + ":" +lobbyId;
        try {
            output.writeUTF(sendInfo);
        } catch (Exception ex) {
            System.out.println("There is a problem while connecting the server.");
            System.out.println(ex);
        }
        readResponse();
        showNicknames();
        setCountryColors();
        setCountryHackerNumLabels();
    }

    private void setCountryHackerNumLabels() {
        ArrayList<String> hackerNumsArray = divideString(hackerNums);
        for (int i = Integer.parseInt(total_num_countries); i < map_pane.getChildren().size(); i++) {
            Label label = (Label) map_pane.getChildren().get(i);
            label.setText(hackerNumsArray.get(i-Integer.parseInt(total_num_countries)));
        }
    }

    private void setCountryColors() {
        ArrayList<String> ownerIdsArray = divideString(ownerIds);
        ArrayList<String> playerIdsArray = divideString(playerIds);
        for (int i = 0; i < Integer.parseInt(total_num_countries); i++) {
            ImageView countryImageView = (ImageView) map_pane.getChildren().get(i);
            if (playerIdsArray.size() >= 1) {
                if (ownerIdsArray.get(i).equals(playerIdsArray.get(0))) {
                    countryImageView.setImage(new Image("/MapComponents/" + (i + 1) + "/" + (i + 1) + "b.png"));
                    continue;
                }
            }
            if (playerIdsArray.size() >= 2) {
                if (ownerIdsArray.get(i).equals(playerIdsArray.get(1))) {
                    countryImageView.setImage(new Image("/MapComponents/" + (i + 1) + "/" + (i + 1) + "r.png"));
                    continue;
                }
            }
            if (playerIdsArray.size() >= 3) {
                if (ownerIdsArray.get(i).equals(playerIdsArray.get(2))) {
                    countryImageView.setImage(new Image("/MapComponents/" + (i + 1) + "/" + (i + 1) + "g.png"));
                    continue;
                }
            }
            if (playerIdsArray.size() >= 4) {
                if (ownerIdsArray.get(i).equals(playerIdsArray.get(3))) {
                    countryImageView.setImage(new Image("/MapComponents/" + (i + 1) + "/" + (i + 1) + "o.png"));
                }
            }
        }
    }

    private void showNicknames() {
        ArrayList<String> nicknamesArray = divideString(nicknames);
        if (Integer.parseInt(numOfPlayers) >= 1) {
            p1Nick_lbl.setText(nicknamesArray.get(0));
        }
        else if (Integer.parseInt(numOfPlayers) >= 2){
            p2Nick_lbl.setText(nicknamesArray.get(1));
        }
        else if (Integer.parseInt(numOfPlayers) >= 3){
            p3Nick_lbl.setText(nicknamesArray.get(2));
        }
        else if (Integer.parseInt(numOfPlayers) >= 4){
            p4Nick_lbl.setText(nicknamesArray.get(3));
        }
    }

    public void readResponse()
    {
        System.out.println("READ RESPONSE IN GameSceneController");
        int playerNumber = 0;
        String playerNicknamesStr = "";
        try {
            //player number
            numOfPlayers = input.readUTF();

            //playerIds
            playerIds = input.readUTF();

            //nicknames
            nicknames = input.readUTF();

            //total country number
            total_num_countries = input.readUTF();

            //owner ids of countries
            ownerIds = "";
            for (int i = 0; i < Integer.parseInt(total_num_countries) ; i++)
            {
                ownerIds += input.readUTF() + ",";
            }
            ownerIds = ownerIds.substring(0, ownerIds.length() - 1);

            //hacker number of each country
            hackerNums = "";
            for (int i = 0; i < Integer.parseInt(total_num_countries); i++)
            {
                hackerNums += input.readUTF() + ",";
            }
            hackerNums = hackerNums.substring(0, hackerNums.length() - 1);
        } catch (IOException e) {
            System.out.println("No answer from server. Trying again...");
        }
    }

    public ArrayList<String> divideString(String strWillBeDivided)
    {
        ArrayList<String> allParts = new ArrayList<>();

            while (!strWillBeDivided.equals("")) {
                int index = 0;
                if (strWillBeDivided.contains(",")) {
                    while (strWillBeDivided.charAt(index) != ',') {
                        index++;
                    }
                    String part = strWillBeDivided.substring(0, index);
                    allParts.add(part);
                    strWillBeDivided = strWillBeDivided.substring(index + 1);
                }
                else {
                    allParts.add(strWillBeDivided);
                    strWillBeDivided = "";
                }
            }
        return allParts;
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
        if (settings_grid.isVisible())
            settings_grid.setVisible(false);
        else
            settings_grid.setVisible(true);
    }

    @FXML
    public void cardsClicked()
    {

    }
}