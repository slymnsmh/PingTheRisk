package Managers;

import DatabaseRelatedClasses.*;
import ServerClasses.Lobby;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameManager
{
    private final int HACKER_NUM_BEGINNING = 30;
    private final int COUNTRY_NUM_BEGINNING = 10;
    private final int TOTAL_NUM_OF_COUNTRIES = 40;
    String[] allColors = {"Color.BLUE", "Color.PURPLE", "Color.GREEN", "Color.RED", "Color.BROWN", "Color.YELLOW", "Color.ORANGE", "Color.TURQUOISE"};
    @FXML Text p1Nick_txt, p2Nick_txt, p3Nick_txt, p4Nick_txt, p5Nick_txt, p6Nick_txt, p7Nick_txt, p8Nick_txt;
    @FXML Label hackerNum_lbl, partName_lbl;
    @FXML Menu hackerNum_menu;
    @FXML Button howToPlay_btn, settings_btn;
    int turnOwner;
    Map map;
    ArrayList<String> playerIds;
    Lobby lobby;

    public GameManager( Lobby lobby, ArrayList<String> playerIds ) throws SQLException
    {
        map = new Map();
        this.lobby = lobby;
        this.playerIds = playerIds;
        startGame();
    }

    public void startGame() throws SQLException
    {
        assignColorsToPlayers();
        assignHackerNumsToPlayers();
        assignCountriesToPlayers();
    }

    public void endGame()
    {
        if ( checkIfFinished() )
        {
            //Finish the game
            uploadDatabase();
        }
        else
        {
            //Start next turn
        }

    }

    public void uploadDatabase()
    {
        //When the game finished, upload all game info to database
    }

    @FXML
    public void howToPlayClicked()
    {}

    @FXML
    public void settingsClicked()
    {}

    public void assignColorsToPlayers() throws SQLException
    {
        for ( int i = 0; i < playerIds.size(); i++ )
        {
            String query = "UPDATE player set color = '"+allColors[i]+"' WHERE id='"+playerIds.get(i)+"'";
            PreparedStatement statement = Database.conn.prepareStatement(query);
            statement.execute();
            System.out.println( (i + 1) + " - Color updated!");
        }
    }

    public void assignHackerNumsToPlayers() throws SQLException
    {
        for ( int i = 0; i < playerIds.size(); i++ )
        {
            String query = "UPDATE player set num_of_hackers = '"+HACKER_NUM_BEGINNING+"' WHERE id='"+playerIds.get(i)+"'";
            PreparedStatement statement = Database.conn.prepareStatement(query);
            statement.execute();
            System.out.println( (i + 1) + " - Hacker updated!");
        }
    }

    public void assignCountriesToPlayers() throws SQLException
    {
        for ( int i = 0; i < lobby.getNumOfPlayers(); i++ )
        {
            String playerId = lobby.getPlayerIdsArray().get(i);
            int[] countryNumbers = generateRandomCountryNumbers();
            String countryNumbersStr = "";
            for ( int j = 0; j < countryNumbers.length; j++ )
            {
                countryNumbersStr += String.valueOf(countryNumbers[j]) + ",";
            }
            countryNumbersStr = countryNumbersStr.substring(0, countryNumbersStr.length() - 1);
            String query = "UPDATE player set num_of_countries = '"+COUNTRY_NUM_BEGINNING+"', countries = '"+countryNumbersStr+"' WHERE id='"+playerId+"'";
            PreparedStatement statement = Database.conn.prepareStatement(query);
            statement.execute();
        }
    }

    public int[] generateRandomCountryNumbers()
    {
        int[] countryNumbers = new int[COUNTRY_NUM_BEGINNING];
        int counter = 0;
        while ( counter != countryNumbers.length )
        {
            int randomNumber = new Random().nextInt(TOTAL_NUM_OF_COUNTRIES);
            boolean isThere = false;
            for ( int i = 0; i < countryNumbers.length; i++ )
            {
                if ( countryNumbers[i] == randomNumber )
                    isThere = true;
            }
            if ( !isThere )
            {
                countryNumbers[counter] = randomNumber;
                counter++;
            }
        }
        return countryNumbers;
    }

    public void assignColorsToCountries()
    {}

    public void assignHackerNumsToCountries()
    {}

    public boolean checkIfFinished()
    {
        return false;
    }

    public void startTurn(int turnOwner)
    {}

    public void setPartNameLabelText(String text)
    {
        partName_lbl.setText(text);
    }

    public void setTurnOwner(int turnOwner)
    {
        this.turnOwner = turnOwner;
    }

    //Oyunculara renk ata
    //Oyunculara hacker sayısı ata
    //Ülkeleri oyunculara ata
    //Ülkelere renk ata
    //Ülkere hacker sayısı ata
    //Eğer tüm ülkeler tek bir kişinin değilse
    //Turn sahibini belirle
    //Turn başlat
    //Eğer tüm ülkeler tek bir kişininse
    //Oyun bilgilerini (kazanan, puanlar vs) güncelle (database)
    //Oyunu bitir ve oyuncuları lobby sayfasına döndür
}