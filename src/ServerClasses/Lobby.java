package ServerClasses;

import DatabaseRelatedClasses.Database;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Lobby
{
    //properties
    private int id;
    private int hostId;
    private int numOfPlayers;
    private String playerIds;
    private Color[] playerColors;
    private Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE};

    public Lobby(String hostId) throws SQLException {
        this.id = generateLobbyId();
        this.hostId = Integer.parseInt(hostId);
        this.numOfPlayers = 1;
        this.playerIds = hostId;
        assignRandomColors();
    }

    public Lobby(int id, int hostID, int numOfPlayers, String playerIds, Color[] playerColors) {
        this.id = id;
        this.hostId = hostID;
        this.numOfPlayers = numOfPlayers;
        this.playerIds = playerIds;
        this.playerColors = playerColors;
    }

    public int generateLobbyId() throws SQLException
    {
        String query = "SELECT * FROM lobby";
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        int randomID = 100000000 + new Random().nextInt(999999999);
        boolean isThere = false;
        while (rs.next())
        {
            if (rs.getInt("id") == randomID)
            {
                isThere = true;
                break;
            }
        }
        if (isThere)
            return generateLobbyId();
        return randomID;
    }

    public void assignRandomColors()
    {
        int counter = 0;
        while (counter < 4)
        {
            int randomIndex = 0;
            do {
                randomIndex = new Random().nextInt(4);
            } while (!(playerColors[randomIndex] == null));
            playerColors[randomIndex] = colors[counter];
            counter++;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostID) {
        this.hostId = hostID;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public String getPlayerIds()
    {
        return playerIds;
    }

    public ArrayList<String> getPlayerIdsArray()
    {
        ArrayList <String> playerIdsArray = new ArrayList<>();

        String id = "";
        for (int i = 0; i < playerIds.length(); i++)
        {
            if (playerIds.charAt(i) == ',')
            {
                playerIdsArray.add(id);
                id = "";
                continue;
            }
            id += playerIds.charAt(i);
        }
        playerIdsArray.add(id);

        return playerIdsArray;
    }

    public void setPlayerIds(String playerIds) {
        this.playerIds = playerIds;
    }

    public Color[] getPlayerColors() {
        return playerColors;
    }

    public void setPlayerColors(Color[] playerColors) {
        this.playerColors = playerColors;
    }
}
