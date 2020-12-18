package ServerClasses;

import DatabaseRelatedClasses.Database;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServerController {
    private Socket socket = null;
    private ServerSocket server = null;
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
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("--- Client" + counter + "accepted ---");
                counter++;
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
                        createPlayer(in, out, inputStr, socket);
                        break;
                    case "join_game":
                        joinGame(in, out, inputStr, socket);
                        break;
                    case "create_lobby":
                        createLobby(in, out, inputStr, socket);
                        break;
                    case "update_lobby":
                        updateLobby(in, out, inputStr, socket);
                }
                //socket.close();
            }

        }
        catch (IOException | SQLException e )
        {
            System.out.println(e);
        }
    }

    public boolean createPlayer(DataInputStream in, DataOutputStream out, String inputStr, Socket createPlayerSocket) throws IOException {
        try {
            System.out.println("Inserting player \"" + inputStr + "\" to database...");
            String playerSocketAddress = createPlayerSocket.getInetAddress().getHostAddress();

            System.out.println("IP:::::: " + playerSocketAddress);
            System.out.println("PORT:::: " + createPlayerSocket.getPort());
            Player p = new Player(inputStr, createPlayerSocket.getInetAddress().getHostAddress(), String.valueOf(createPlayerSocket.getPort()));
            p.setCreatePlayerSocket(createPlayerSocket);
            System.out.println(p.getId());
            String query = "INSERT INTO player (id, game_id, nickname, color, score, num_of_hackers, num_of_countries, countries, num_of_wins, num_of_losses, num_of_bonus_cards, num_of_bonus_hackers, is_online)"
                    +"VALUES ('"+p.getId()+"','0','"+inputStr+"', '-', '0', '0', '0', '-', '0', '0', '0', '0', '1')";
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
            //socket.close();
            System.out.println(i);
            System.out.println("Couldn't insert player \"" + inputStr + "\" to database.");
            return false;
        }
    }

    public boolean joinGame(DataInputStream in, DataOutputStream out, String inputStr, Socket joinGameSocket) throws SQLException, IOException {
        int index = 0;
        while (inputStr.charAt(index) != ':')
            index++;
        String lobbyId = inputStr.substring(0, index);
        String playerId = inputStr.substring(index + 1, inputStr.length());
        for (Player p : players)
        {
            if (p.getId() == Integer.valueOf(playerId)) {
                p.setJoinGameSocket(joinGameSocket);
                p.setGameId(Integer.parseInt(lobbyId));
            }
        }
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
/*
    public void getNicknames() throws SQLException {
        ArrayList<String> playerIds = lobby.getPlayerIdsArray();
        ArrayList<String> playerNicknames = new ArrayList<>();
        for (String j : playerIds) {
            String query = "SELECT * from player WHERE id='" + j + "'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rsPlayer = Database.stmt.executeQuery(query);
            rsPlayer.next();
            playerNicknames.add(rsPlayer.getString("nickname"));
        }
    }
*/
    public boolean createLobby(DataInputStream in, DataOutputStream out, String hostId, Socket createGameSocket) {
        Lobby lobby = null;
        try {
            System.out.println("BEFORE LOBBY CREATED");
            lobby = new Lobby(hostId);
            System.out.println("LOBBY CREATED");
            lobbies.add(lobby);
            for (Player p : players)
            {
                if (p.getId() == Integer.valueOf(hostId)) {
                    p.setGameId(lobby.getId());
                    p.setCreateLobbySocket(createGameSocket);
                }
            }
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

    public void updateLobby(DataInputStream in, DataOutputStream out, String inputStr, Socket updateLobbySocket) throws SQLException {
        int index = 0;
        while (inputStr.charAt(index) != ':')
            index++;
        String playerId = inputStr.substring(0, index);
        System.out.println("INPUT P ID: " + playerId);
        String lobbyId = inputStr.substring(index + 1, inputStr.length());
        System.out.println("INPUT L ID: " + lobbyId);
        String query = "SELECT * from lobby WHERE id='" + lobbyId + "'";
        Database.connect();
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        rs.next();
        Lobby lobby = new Lobby(rs.getInt("id"), rs.getInt("host_id"), rs.getInt("num_of_players"),
                rs.getString("player_IDs"), new Color[]{});
        for (Player p : players) {
            if (p.getId() == Integer.valueOf(playerId))
                p.setUpdateLobbySocket(updateLobbySocket);
            System.out.println("player id: " + p.getId());
            System.out.println("PLAYER'S GAME ID: " + p.getGameId());
            System.out.println("LOBBY ID: " + lobbyId);
            if (p.getGameId() == Integer.valueOf(lobbyId)) {
                System.out.println("player ıds: " + lobby.getPlayerIds());
                if (!(lobby.getPlayerIds().contains(String.valueOf(p.getId())))) {
                    System.out.println("PLAYER IP: " + p.getIp());
                    System.out.println("PLAYER PORT: " + p.getUpdateLobbySocket().getPort());
                    Socket socket = null;
                    try {
                        socket = p.getUpdateLobbySocket();
                        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF("+upload+");//upload permission
                        System.out.println("UPLOAD MESSAGE SENT.");
                        out.writeUTF(String.valueOf(lobby.getNumOfPlayers())); //player number
                        System.out.println("PLAYER NUMBER SENT.");
                        query = "SELECT * FROM player WHERE id = '" + p.getId() + "'";
                        rs = Database.stmt.executeQuery(query);
                        rs.next();
                        System.out.println("PLAYER'S NICKNAME: " + rs.getString("nickname"));
                        out.writeUTF(rs.getString("nickname")); //nickname
                        System.out.println("NICKNAME SENT.");
                    } catch (IOException e) {
                        System.out.println("SOCKET OLMADI!");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String args[])
    {
        ServerController server = new ServerController(2641);
    }
}