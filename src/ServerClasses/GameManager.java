package ServerClasses;

import DatabaseRelatedClasses.*;
import ServerClasses.Player;
import javafx.fxml.FXML;

import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameManager
{
    private final int HACKER_NUM_BEGINNING = 30;
    private final int COUNTRY_NUM_BEGINNING = 10;
    private final int TOTAL_NUM_OF_COUNTRIES = 35;
    String[] allColors = {"BLUE", "RED", "GREEN", "ORANGE"};
    int turnOwner;
    Map map;
    ArrayList<String> playerIds;
    ArrayList<Player> players;
    Lobby lobby;
    Socket socket;

    public GameManager(Socket socket, ArrayList<Player> players, Lobby lobby) throws SQLException {
        this.socket = socket;
        this.players = players;
        this.lobby = lobby;
        this.playerIds = lobby.getPlayerIdsArray();
        startGame();
    }

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

    public void assignColorsToPlayers() throws SQLException
    {
        for ( int i = 0; i < playerIds.size(); i++ )
        {
            for (Player p : players)
            {
                if (p.getId() == Integer.parseInt(playerIds.get(i)))
                {
                    p.setColor(allColors[i]);

                    String query = "UPDATE player set color = '"+allColors[i]+"' WHERE id='"+playerIds.get(i)+"'";
                    PreparedStatement statement = Database.conn.prepareStatement(query);
                    statement.execute();
                    System.out.println( (i + 1) + " - Color updated!");
                }
            }
        }
    }

    public void assignHackerNumsToPlayers() throws SQLException
    {
        for ( int i = 0; i < playerIds.size(); i++ )
        {
            for (Player p : players) {
                if (p.getId() == Integer.parseInt(playerIds.get(i))) {
                    p.setNumOfHackers(HACKER_NUM_BEGINNING);

                    String query = "UPDATE player set num_of_hackers = '" + HACKER_NUM_BEGINNING + "' WHERE id='" + playerIds.get(i) + "'";
                    PreparedStatement statement = Database.conn.prepareStatement(query);
                    statement.execute();
                    System.out.println((i + 1) + " - Hacker updated!");
                }
            }
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