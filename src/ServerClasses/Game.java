package ServerClasses;

import java.util.ArrayList;

public class Game
{
    //properties
    int id;
    int num_of_players;
    ArrayList<Player> players;

    public Game(Lobby lobby)
    {
        id = lobby.getId();
        num_of_players = 0;
        players = new ArrayList<>();
    }

    public boolean addPlayer(Player p)
    {
        if (players.add(p))
        {
            num_of_players++;
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player p)
    {
        if (players.remove(p))
        {
            num_of_players--;
            return true;
        }
        return false;
    }

    public Player getPlayer(int id)
    {
        for (Player p : players)
        {
            if (p.getId() == id)
                return p;
        }
        System.out.println("Player is not found in the players list!");
        return null;
    }
}
