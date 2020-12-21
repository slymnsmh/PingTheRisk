import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameManager
{
    int playerNumber;
    private final int TOTAL_NUM_OF_COUNTRIES = 36;
    private final int TOTAL_NUM_OF_HACKERS = 120;
    String[] allColors = {"BLUE", "RED", "GREEN", "ORANGE"};
    int turnOwner;
    //Map map;
    ArrayList<String> playerIds;
    Lobby lobby;
    Socket socket;
    int hackerNumBeginning;
    int countryNumBeginning;
    ArrayList<Integer> givenCountries = new ArrayList<>();
    DataInputStream in;
    DataOutputStream out;
    ArrayList<Country> allCountries = ServerController.countries;

    public GameManager(Socket socket, DataInputStream in, DataOutputStream out, Lobby lobby) throws SQLException, IOException {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.lobby = lobby;
        playerNumber = lobby.getNumOfPlayers();
        this.playerIds = lobby.getPlayerIdsArray();
        hackerNumBeginning = TOTAL_NUM_OF_HACKERS / playerNumber;
        countryNumBeginning = TOTAL_NUM_OF_COUNTRIES / playerNumber;
        System.out.println("I CAMED HERE");
        startGame();
    }

    public void startGame() throws SQLException, IOException {
        assignColorsToPlayers();
        assignHackerNumsToPlayers();
        assignCountriesToPlayers();
        assignHackerNumsToCountries();
        int counter = 0;
        String nicknames = "";
        for (Player p : ServerController.players)
        {
            if (lobby.getPlayerIds().contains(String.valueOf(p.getId()))) {
                nicknames += p.getNickname() + ",";
                counter++;
                System.out.println("PLAYER: " + counter);
                System.out.println("ID: " + p.getId());
                System.out.println("NICK: " + p.getNickname());
                System.out.println("COLOR: " + p.getColor());
                for (int i = 0; i < p.getCountries().size(); i++) {
                    System.out.println("COUNTRY " + i + ": " + p.getCountries().get(i) + " -> hacker num: " + allCountries.get(p.getCountries().get(i) - 1).hackerNumber);
                }
                System.out.println("NUM OF COUNTRIES: " + p.getNumOfCountries());
                System.out.println("NUM OF HACKERS: " + p.getNumOfHackers());
            }
        }
        nicknames = nicknames.substring(0, nicknames.length() - 1);
        out.writeUTF(String.valueOf(lobby.getNumOfPlayers()));
        out.writeUTF(lobby.getPlayerIds());
        //send colors !!!!!!!!
        out.writeUTF(nicknames);
        out.writeUTF(String.valueOf(TOTAL_NUM_OF_COUNTRIES));
        for (int i = 0; i < TOTAL_NUM_OF_COUNTRIES; i++) {
            out.writeUTF(String.valueOf(allCountries.get(i).owner.getId()));
        }
        for (int i = 0; i < TOTAL_NUM_OF_COUNTRIES; i++) {
            out.writeUTF(String.valueOf(allCountries.get(i).hackerNumber));
        }
    }

    public void assignColorsToPlayers() throws SQLException
    {
        for ( int i = 0; i < playerIds.size(); i++ )
        {
            for (Player p : ServerController.players)
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
            for (Player p : ServerController.players) {
                if (p.getId() == Integer.parseInt(playerIds.get(i))) {
                    p.setNumOfHackers(hackerNumBeginning);

                    String query = "UPDATE player set num_of_hackers = '" + hackerNumBeginning + "' WHERE id='" + playerIds.get(i) + "'";
                    PreparedStatement statement = Database.conn.prepareStatement(query);
                    statement.execute();
                    System.out.println((i + 1) + " - Hacker updated!");
                }
            }
        }
    }

    public void assignCountriesToPlayers() throws SQLException
    {
        for ( int i = 0; i < playerIds.size(); i++ )
        {
            for (Player p : ServerController.players)
            {
                if (p.getId() == Integer.parseInt(playerIds.get(i)))
                {
                    String playerId = playerIds.get(i);
                    ArrayList<Integer> countryNumbers = generateRandomCountryNumbers(p);
                    p.setCountries(countryNumbers);
                    p.setNumOfCountries(countryNumBeginning);

                    String countryNumbersStr = "";

                    for (int j = 0; j < countryNumbers.size(); j++) {
                        countryNumbersStr += countryNumbers.get(j) + ",";
                    }

                    countryNumbersStr = countryNumbersStr.substring(0, countryNumbersStr.length() - 1);
                    String query = "UPDATE player set num_of_countries = '" + countryNumBeginning + "', countries = '" + countryNumbersStr + "' WHERE id='" + playerId + "'";
                    PreparedStatement statement = Database.conn.prepareStatement(query);
                    statement.execute();
                }
            }
        }
    }

    public ArrayList<Integer> generateRandomCountryNumbers(Player player)
    {
        int[] countryNumbers = new int[countryNumBeginning];
        int counter = 0;
        while ( counter != countryNumbers.length )
        {
            int randomNumber = new Random().nextInt(TOTAL_NUM_OF_COUNTRIES) + 1;
            boolean isThere = false;
            for ( int i = 0; i < countryNumbers.length; i++ )
            {
                if ( countryNumbers[i] == randomNumber )
                    isThere = true;
            }
            for (int i = 0; i < givenCountries.size(); i++)
            {
                if (givenCountries.get(i) == randomNumber)
                    isThere = true;
            }
            if ( !isThere )
            {
                //allCountries.get(randomNumber - 1).owner = player;
                countryNumbers[counter] = randomNumber;
                givenCountries.add(randomNumber);
                counter++;
            }
        }
        ArrayList<Integer> countryNums = new ArrayList<>();
        for (int a : countryNumbers)
        {
            countryNums.add(a);
        }
        return countryNums;
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

    public void assignHackerNumsToCountries() {
        System.out.println("ASSIGN ÇALIŞTI!");
        int minHackerForACountry = hackerNumBeginning / countryNumBeginning;
        System.out.println("MIN HACKER FOR A COUNTRY: " + minHackerForACountry);
        String checker = "";
        for (Country country : allCountries)
        {
            if (checker.equals("..")) {
                country.hackerNumber = minHackerForACountry + 1;
                System.out.println(country.id + " -> hacker num ===> " + country.hackerNumber);
                checker = "";
            }
            else if (checker.equals("") || checker.equals(".")) {
                country.hackerNumber = minHackerForACountry;
                System.out.println(country.id + " -> hacker num ===> " + country.hackerNumber);
                checker += ".";
            }
        }
        for (int i = 0; i < playerIds.size(); i++) {
            for (Player p : ServerController.players) {
                if (p.getId() == Integer.parseInt(playerIds.get(i))) {
                    for (Integer countryId : p.getCountries())
                    {
                        for (Country country : allCountries)
                        {
                            if (countryId == country.id)
                            {
                                country.owner = p;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void uploadDatabase()
    {
        //When the game finished, upload all game info to database
    }

    /*@FXML
    public void howToPlayClicked()
    {}

    @FXML
    public void settingsClicked()
    {}*/

    public void assignColorsToCountries()
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