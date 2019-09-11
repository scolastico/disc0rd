package disc0rd.database;

import java.sql.Connection;
import java.util.ArrayList;

public class DatabaseManager {

    private ArrayList<Pr0User> _pr0Users;
    private ArrayList<Server> _server;
    private ArrayList<Sub> _sub;

    private Connection connection;

    private static DatabaseManager _instance = null;

    public static DatabaseManager getInstance() {
        if(_instance == null) {
            _instance = new DatabaseManager();
        }
        return _instance;
    }

    private DatabaseManager() {



    }

    public DatabaseManager saveDatabase() {



        return this;
    }

    public Server getServer(int serverId) {
        for (Server server:_server) {
            if (server.getId() == serverId) {
                return server;
            }
        }
        Server server = new Server(serverId);
        _server.add(server);
        return server;
    }

    public ArrayList<Server> getAllServers() {
        return _server;
    }

    public Pr0User getPr0User(String userName) {
        for (Pr0User pr0User:_pr0Users) {
            if (pr0User.getUsername().equalsIgnoreCase(userName)) {
                return pr0User;
            }
        }
        Pr0User pr0User = new Pr0User(userName);
        _pr0Users.add(pr0User);
        return pr0User;
    }

    public ArrayList<Pr0User> getAllPr0Users() {
        return _pr0Users;
    }

    public DatabaseManager addSub(int serverId, int channelId, String pr0UserName) {
        for (Sub sub:_sub) {
            if(sub.getServer().getId() == serverId && sub.getPr0User().getUsername().equalsIgnoreCase(pr0UserName) && sub.getChannelId() == channelId) {
                return this;
            }
        }
        _sub.add(new Sub(getServer(serverId), getPr0User(pr0UserName), channelId));
        return this;
    }

    public DatabaseManager addSub(Server server, int channelId, Pr0User pr0User) {
        for (Sub sub:_sub) {
            if(sub.getServer().equals(server) && sub.getPr0User().equals(pr0User) && sub.getChannelId() == channelId) {
                return this;
            }
        }
        _sub.add(new Sub(server, pr0User, channelId));
        return this;
    }

    public DatabaseManager removeSub(Sub sub) {
        if (_sub.contains(sub)) {
            _sub.remove(sub);
        }
        return this;
    }

    public ArrayList<Sub> getAllSubs() {
        return _sub;
    }

}
