package Controllers;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import Scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SettingsSceneController implements Initializable
{
    @FXML ImageView background_img;
    @FXML AnchorPane main_pane;
    @FXML Pane menu_pane;
    @FXML MenuButton display_menuBtn;
    @FXML Slider sound_slider;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        readFile();
    }

    @FXML
    public void saveClicked() throws IOException
    {
        String filePath = "src/Controllers/Settings.txt";
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer buffer = new StringBuffer();
        while (sc.hasNextLine())
        {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();
        System.out.println("Contents of the file: "+fileContents);
        sc.close();

        String oldFrameSize = "\\d+[x]\\d+";
        String newFrameSize = display_menuBtn.getText();
        String oldSound = "\\d+[.]\\d+";
        String newSound = String.valueOf(sound_slider.getValue());

        fileContents = fileContents.replaceAll(oldSound, newSound);
        fileContents = fileContents.replaceAll(oldFrameSize, newFrameSize);

        FileWriter writer = new FileWriter(filePath);
        System.out.println("new data: "+fileContents);
        writer.append(fileContents);
        writer.flush();
        applySettings();
    }

    public void readFile()
    {
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader("src/Controllers/settings.txt"));
            String line = reader.readLine();
            display_menuBtn.setText(line);
            line = reader.readLine();
            sound_slider.adjustValue(Double.parseDouble(line));
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void applySettings() throws IOException
    {
        double width, height;
        int index = 0;
        while (display_menuBtn.getText().charAt(index) != 'x')
        {
            index++;
        }
        width = Double.parseDouble(display_menuBtn.getText().substring(0, index));
        height = Double.parseDouble(display_menuBtn.getText().substring(index + 1, display_menuBtn.getText().length()));
        System.out.println("W: " + width + "\nH: " + height);

        Main.stage.setHeight(height);
        Main.stage.setWidth(width);
        main_pane.setPrefHeight(height);
        main_pane.setPrefWidth(width);
        menu_pane.setPrefHeight(height);
        menu_pane.setPrefWidth(width);
        background_img.setFitHeight(height);
        background_img.setFitWidth(width);
        Main.stage.setMaximized(true);

        //Main.stage.show();

        //System.out.println(newScene.getWidth() + " - " + newScene.getHeight());
    }

    @FXML
    public void menuItemClicked( ActionEvent event )
    {
        MenuItem clicked = (MenuItem) event.getSource();
        display_menuBtn.setText(clicked.getText());
    }

    public void mainMenuClicked() throws IOException
    {
        MainScene mainScene = new MainScene();
    }

    /*private void changeScene(String filePath) throws IOException
    {
        Parent newGameMenuParent = FXMLLoader.load(getClass().getResource(filePath));
        Scene newGameMenuScene = new Scene(newGameMenuParent);
        Main.stage.setScene(newGameMenuScene);
        Main.stage.show();
    }*/
}
