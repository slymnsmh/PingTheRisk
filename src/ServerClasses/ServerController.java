package ServerClasses;

import Controllers.LobbySceneController;
import Controllers.MainSceneController;
import DatabaseRelatedClasses.Database;
import Scene.LobbyScene;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServerController {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
    private ArrayList<Game> games = new ArrayList<Game>();

    public ServerController(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started!");
            int counter = 1;
            while (true)
            {
                socket = server.accept();
                System.out.println("--- Client" + counter + "accepted ---");
                counter++;
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());
                String command = in.readUTF();
                int index = 0;
                while (command.charAt(index) != ':')
                    index++;
                String inputStr = command.substring(index + 1, command.length());
                System.out.println("Input: " + inputStr);
                command = command.substring(0, index);
                System.out.println("Command: " + command);
                switch (command){
                    case "create_player":
                        createPlayer(inputStr);
                        break;
                    case "join_game":
                        joinGame(inputStr);
                        break;
                    case "create_lobby":
                        createLobby(inputStr);
                        break;
                }
                socket.close();
            }

        }
        catch (IOException | SQLException e )
        {
            System.out.println(e);
        }
    }

    public boolean createPlayer(String inputStr) throws IOException {
        try {
            System.out.println("Inserting player \"" + inputStr + "\" to database...");
            Player p = new Player(inputStr);
            System.out.println(p.getId());
            String query = "INSERT INTO player (id, nickname, color, score, num_of_hackers, num_of_countries, countries, num_of_wins, num_of_losses, num_of_bonus_cards, num_of_bonus_hackers, is_online)"
                    +"VALUES ('"+p.getId()+"','"+inputStr+"', '-', '0', '0', '0', '-', '0', '0', '0', '0', '1')";
            Database.stmt = Database.conn.createStatement();
            if (Database.stmt != null)
                Database.stmt.executeUpdate(query);
            else
                System.out.println("ANANANANANN");
            players.add(p);
            out.writeUTF(String.valueOf(p.getId()));
            out.writeUTF("+ok+");
            System.out.println("Inserted player \"" + inputStr + "\" to database.");
            return true;
        } catch (SQLException i) {
            socket.close();
            System.out.println(i);
            System.out.println("Couldn't insert player \"" + inputStr + "\" to database.");
            return false;
        }
    }

    public boolean joinGame(String inputStr) throws SQLException, IOException {
        int index = 0;
        while (inputStr.charAt(index) != ':')
            index++;
        String lobbyId = inputStr.substring(0, index);
        String playerId = inputStr.substring(index + 1, inputStr.length());
        System.out.println("Joining player \"" + playerId + "to lobby \"" + lobbyId + "\"...");
        String query = "SELECT * from lobby WHERE id='"+lobbyId+"'";
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        if (rs.next())
        {
            query = "UPDATE lobby set num_of_players = num_of_players + 1, player_IDs = concat(player_IDs, ',"+ playerId +"')" +
                    "WHERE id = '" + lobbyId + "'";
            Database.stmt = Database.conn.createStatement();
            Database.stmt.executeUpdate(query);

            /*query = "SELECT * from lobby WHERE id='"+lobbyId+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            rs = Database.stmt.executeQuery(query);
            rs.next();*/

            Lobby lobby = new Lobby(rs.getInt("id"), rs.getInt("host_id"), rs.getInt("num_of_players"), rs.getString("player_IDs"), new Color[]{Color.RED});
            lobbies.add(lobby);
            System.out.println("LOBBY: ");
            System.out.println(rs.getString("player_IDs"));
            System.out.println(rs.getInt("num_of_players"));
            System.out.println("---------------------------");
            out.writeUTF("+ok+");
            return true;
        }
        else
        {
            out.writeUTF("+invalid_lobby_id+");
            return false;
        }
    }

    public boolean createLobby(String inputStr) {
        Lobby lobby = null;
        try {
            System.out.println("BEFORE LOBBY CREATED");
            lobby = new Lobby(inputStr);
            System.out.println("LOBBY CREATED");
            lobbies.add(lobby);
            String query = "INSERT INTO lobby (id, host_id, num_of_players, player_IDs, player_colors)"
                    + "VALUES ( '" + lobby.getId() + "','" + lobby.getHostId() + "', '1', '" + lobby.getHostId() + "', '" + lobby.getPlayerColors()[0] + "')";
            Database.stmt = Database.conn.createStatement();
            Database.stmt.executeUpdate(query);
            out.writeUTF(String.valueOf(lobby.getId()));
            out.writeUTF("+ok+");
            return true;
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static void main(String args[])
    {
        ServerController server = new ServerController(2641);
    }
}