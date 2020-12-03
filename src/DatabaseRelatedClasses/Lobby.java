package DatabaseRelatedClasses;

public class Lobby
{
    //properties
    private int id;
    private int hostID;
    private int numOfPlayers;
    private String playerIds;
    private String[] playerColors;

    public Lobby(int id, int hostID, int numOfPlayers, String playerIds, String[] playerColors) {
        this.id = id;
        this.hostID = hostID;
        this.numOfPlayers = numOfPlayers;
        this.playerIds = playerIds;
        this.playerColors = playerColors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public String getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(String playerIds) {
        this.playerIds = playerIds;
    }

    public String[] getPlayerColors() {
        return playerColors;
    }

    public void setPlayerColors(String[] playerColors) {
        this.playerColors = playerColors;
    }
}
