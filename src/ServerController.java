import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map;

public class ServerController {
    private Socket socket = null;
    private ServerSocket server = null;
    public static ArrayList<Player> players = new ArrayList<Player>();
    public static ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
    private ArrayList<Game> games = new ArrayList<Game>();
    boolean canUpload = true;
    HashMap<Socket, Boolean> requests = new HashMap<>();
    public static ArrayList<Country> countries = new ArrayList<>();

    public ServerController(int port) {
        try {
            countries = getCountries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            server = new ServerSocket(port);
            System.out.println("Server started!");
            int counter = 1;
            while (true)
            {
                System.out.println("AAA: " + counter);
                socket = server.accept();
                //requests.put(socket, false);
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("--- Client" + counter + "accepted ---");
                counter++;
                String wholeCommand = in.readUTF();
                String command = wholeCommand.substring(0, wholeCommand.indexOf(":"));
                String inputStr = wholeCommand.substring(wholeCommand.indexOf(":") + 1);
                System.out.println("Command: " + command);
                System.out.println("Input: " + inputStr);
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
                        break;
                    case "get_game_info":
                        String lobbyId = inputStr.substring(inputStr.indexOf(":") + 1);
                        System.out.println("GET GAME INFO RECEIVED");
                        Lobby lobby = null;
                        for (Lobby l : lobbies)
                        {
                            if (l.getId() == Integer.parseInt(lobbyId))
                                lobby = l;
                        }
                        GameManager gameManager = new GameManager(socket, in, out, lobby);
                        break;
                    case "start_game":
                        startGame(socket, in, out, inputStr);
                        break;
                }
                //socket.close();
            }

        }
        catch (IOException | SQLException e )
        {
            System.out.println(e);
        }
    }

    public ArrayList<Country> getCountries() throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();

        Database.connect();
        String query = "SELECT * from country";
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        while (rs.next())
        {
            Country country = new Country(rs.getInt("id"), rs.getString("name"), rs.getString("location"));
            countries.add(country);
        }
        return countries;
    }

    public Socket getLastSocket()
    {
        for (Map.Entry<Socket, Boolean> entry : requests.entrySet())
        {
            if (entry.getValue())
            {
                System.out.println("RETURN SOCKET: " + socket.getInetAddress().getHostAddress());
                return entry.getKey();
            }
        }

        System.out.println("!!!RETURN NULL!!!");
        return null;
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
            requests.replace(socket, true);
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
        String playerId = inputStr.substring(0, index);
        String lobbyId = inputStr.substring(index + 1, inputStr.length());
        System.out.println("LOBBY JOIN P ID: " + playerId);
        System.out.println("LOBBY JOIN L ID: " + lobbyId);
        for (Lobby l : lobbies)
        {
            if (l.getId() == Integer.parseInt(lobbyId))
            {
                l.setNumOfPlayers(l.getNumOfPlayers() + 1);
                l.setPlayerIds(l.getPlayerIds() +","+ playerId);
            }
        }
        for (Player p : players)
        {
            if (p.getId() == Integer.parseInt(playerId)) {
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
            System.out.println("LOBBY: ");
            System.out.println(rs.getString("player_IDs"));
            System.out.println(rs.getInt("num_of_players"));
            System.out.println("---------------------------");
            out.writeUTF("+ok+");
            requests.replace(socket, true);
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
                if (p.getId() == Integer.parseInt(hostId)) {
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
            requests.replace(socket, true);
            return true;
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /*public void updateLobby(DataInputStream in, DataOutputStream out, String inputStr, Socket updateLobbySocket) throws SQLException {
        int index = 0;
        while (inputStr.charAt(index) != ':')
            index++;
        String playerId = inputStr.substring(0, index);//
        System.out.println("INPUT P ID: " + Integer.parseInt(playerId));
        String lobbyId = inputStr.substring(index + 1, inputStr.length());
        System.out.println("INPUT L ID: " + Integer.parseInt(lobbyId));
        Lobby lobby = null;
        for (Lobby l : lobbies)
        {
            if (l.getId() == Integer.parseInt(lobbyId))
            {
                lobby = l;
            }
        }
        for (String pIdStr : lobby.getPlayerIdsArray()) {
            Player p = null;
            System.out.println("pIdStr: " + pIdStr);
            for (Player p1 : players) {
                if (p1.getId() == Integer.parseInt(pIdStr)) {
                    p = p1;
                    break;
                }
            }
            if (p.getId() == Integer.parseInt(playerId))
                p.setUpdateLobbySocket(updateLobbySocket);
            System.out.println("player id: " + p.getId());
            System.out.println("PLAYER'S GAME ID: " + p.getGameId());
            System.out.println("LOBBY ID: " + lobbyId);
            if (p.getGameId() == Integer.parseInt(lobbyId)) {
                System.out.println("player ıds: " + lobby.getPlayerIds());
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
                    System.out.println("PLAYER'S NICKNAME: " + p.getNickname());
                    String nicknames = "";
                    for (String pId : lobby.getPlayerIdsArray())
                    {
                        for (Player p1 : players)
                        {
                            if (p1.getId() == Integer.parseInt(pId))
                            {
                                nicknames += p1.getNickname() + ",";
                                break;
                            }
                        }
                    }
                    nicknames = nicknames.substring(0, nicknames.length() - 1);
                    out.writeUTF(nicknames); //nicknames
                    System.out.println("NICKNAMES SENT.");
                } catch (IOException e) {
                    System.out.println("SOCKET OLMADI!");
                    String playerIds = lobby.getPlayerIds();
                    if (playerIds.contains(String.valueOf(p.getId())))
                    {
                        System.out.println("OLD PLAYER IDS: " + playerIds);
                        int indexId = playerIds.indexOf(String.valueOf(p.getId()));
                        System.out.println("INDEX: " + indexId);
                        playerIds = playerIds.substring(0, indexId);
                        if ((indexId + 10) < lobby.getPlayerIds().length())
                            lobby.setPlayerIds(lobby.getPlayerIds().substring(indexId + 10));
                        else
                            lobby.setPlayerIds(playerIds.substring(0, playerIds.length() - 1));
                        lobby.setNumOfPlayers(lobby.getNumOfPlayers() - 1);
                        System.out.println("NUM OF PLAYERS: " + lobby.getNumOfPlayers());
                        System.out.println("PLAYER " + p.getId() + " IS REMOVED.");
                        System.out.println("NEW PLAYEYR IDS: " + lobby.getPlayerIds());
                    }
                    players.remove(p);
                    if (p.getId() == lobby.getHostId() && lobby.getNumOfPlayers() > 0)
                    {
                        lobby.setHostId(Integer.parseInt(lobby.getPlayerIds().substring(0, 9)));
                    }
                    e.printStackTrace();
                }
            }
        }
    }*/

    public void updateLobby(DataInputStream in, DataOutputStream out, String inputStr, Socket updateLobbySocket) throws IOException {
        canUpload = false;
        String playerMethodStarterId = inputStr.substring(0, inputStr.indexOf(":"));
        String lobbyId = inputStr.substring(inputStr.indexOf(":") + 1);
        for (Player p : players)
        {
            if (p.getId() == Integer.parseInt(playerMethodStarterId)) {
                p.setUpdateLobbySocket(updateLobbySocket);
                p.setIp(updateLobbySocket.getInetAddress().getHostAddress());
                p.setPort(String.valueOf(updateLobbySocket.getPort()));
                System.out.println("NICK: " + p.getNickname());
                System.out.println("IP: " + p.getIp());
                System.out.println("PORT: "+ p.getPort());
            }
        }
        System.out.println("Updating player " + playerMethodStarterId + " to lobby " + lobbyId);
        Lobby lobby = null;
        for (Lobby l : lobbies)
        {
            if (l.getId() == Integer.parseInt(lobbyId))
            {
                lobby = l;
            }
        }
        for (String playerIdStr : lobby.getPlayerIdsArray())
        {
            Player player = null;
            for (Player p : players)
            {
                if (p.getId() == Integer.parseInt(playerIdStr))
                {
                    player = p;
                }
            }
            /*if (player.getId() != Integer.parseInt(playerMethodStarterId))
            {*/
            in = new DataInputStream(new BufferedInputStream(player.getUpdateLobbySocket().getInputStream()));
            out = new DataOutputStream(player.getUpdateLobbySocket().getOutputStream());
            out.writeUTF("+upload+");
            out.writeUTF(String.valueOf(lobby.getNumOfPlayers()));
            String playerNicknames = "";
            for (Player p : players)
            {
                if (lobby.getPlayerIds().contains(String.valueOf(p.getId())))
                    playerNicknames += p.getNickname() + ",";
            }
            out.writeUTF(playerNicknames);
            //}
        }
    }

    public void startGame(Socket socket, DataInputStream in, DataOutputStream out, String inputStr) throws IOException {
        System.out.println("START GAME RECEIVED");
        String lobbyId = inputStr.substring(inputStr.indexOf(":")+1);
        Lobby lobby = null;
        for (Lobby l : lobbies)
        {
            if (l.getId() == Integer.parseInt(lobbyId))
                lobby = l;
        }
        for (String playerId : lobby.getPlayerIdsArray())
        {
            Player player = null;
            for (Player p : players)
            {
                if (p.getId() == Integer.parseInt(playerId))
                    player = p;
            }
            Socket playerLobbySocket = player.getUpdateLobbySocket();
            //DataOutputStream output = new DataOutputStream(playerLobbySocket.getOutputStream());
            out.writeUTF("+go_to_game_scene+");
        }
    }

    public static void main(String args[])
    {
        ServerController server = new ServerController(2641);
    }
}