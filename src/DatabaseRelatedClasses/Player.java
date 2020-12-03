package DatabaseRelatedClasses;

public class Player
{
    //properties
    private int id;
    private String nickname;
    private String color;
    private int numOfHackers;
    private int numOfRegions;
    private int numOfWins;
    private int numOfLosses;
    private int numOfBonusCards;
    private int numOfBonusHackers;
    private boolean isOnline;

    public Player(int id, String nickname)
    {
        this.id = id;
        this.nickname = nickname;
        this.color = "";
        this.numOfHackers = 0;
        this.numOfRegions = 0;
        this.numOfWins =0;
        this.numOfLosses = 0;
        this.numOfBonusCards = 0;
        this.numOfBonusHackers = 0;
        this.isOnline = true;
    }
    public Player(int id, String nickname, String color, int numOfHackers, int numOfRegions, int numOfWins, int numOfLosses, int numOfBonusCards, int numOfBonusHackers, boolean isOnline)
    {
        this.id = id;
        this.nickname = nickname;
        this.color = color;
        this.numOfHackers = numOfHackers;
        this.numOfRegions = numOfRegions;
        this.numOfWins =numOfWins;
        this.numOfLosses = numOfLosses;
        this.numOfBonusCards = numOfBonusCards;
        this.numOfBonusHackers = numOfBonusHackers;
        this.isOnline = isOnline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumOfHackers() {
        return numOfHackers;
    }

    public void setNumOfHackers(int numOfHackers) {
        this.numOfHackers = numOfHackers;
    }

    public int getNumOfRegions() {
        return numOfRegions;
    }

    public void setNumOfRegions(int numOfRegions) {
        this.numOfRegions = numOfRegions;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    public int getNumOfLosses() {
        return numOfLosses;
    }

    public void setNumOfLosses(int numOfLosses) {
        this.numOfLosses = numOfLosses;
    }

    public int getNumOfBonusCards() {
        return numOfBonusCards;
    }

    public void setNumOfBonusCards(int numOfBonusCards) {
        this.numOfBonusCards = numOfBonusCards;
    }

    public int getNumOfBonusHackers() {
        return numOfBonusHackers;
    }

    public void setNumOfBonusHackers(int numOfBonusHackers) {
        this.numOfBonusHackers = numOfBonusHackers;
    }

    public boolean getOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
