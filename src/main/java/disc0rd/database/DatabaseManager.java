package disc0rd.database;

import disc0rd.config.Settings;
import io.sentry.Sentry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DatabaseManager {

    private ArrayList<Pr0User> _cache_pr0Users;
    private ArrayList<Server> _cache_server;
    private ArrayList<Sub> _cache_sub;
    private Connection _connection;
    private String _prefix;

    private static DatabaseManager _instance = null;

    public static void resetInstance() {
        if (_instance != null) {
            try {
                _instance._connection.close();
            } catch (Exception ignored) {}
            _instance = null;
        }
    }

    public static DatabaseManager getInstance() {
        if(_instance == null) {
            _instance = new DatabaseManager();
        }
        return _instance;
    }

    private DatabaseManager() {

        Settings settings = Settings.getInstance();
        _prefix = settings.getString("mysql.prefix");

        try {

            _connection = DriverManager.getConnection("jdbc:mysql://" + settings.getString("mysql.host") + ":" + settings.getString("mysql.port") + "/" + settings.getString("mysql.database"), settings.getString("mysql.user"), settings.getString("mysql.password"));

        } catch (Exception error) {

            System.out.println("Database Error:");
            error.printStackTrace();
            System.out.println("[info] Sending use statistics...");
            Sentry.getContext().addTag("type", "DB-ERROR");
            Sentry.capture(error);
            Sentry.close();
            System.exit(1);
            return;

        }

    }

    public DatabaseManager resetCache() {
        saveDatabase();
        _cache_pr0Users = new ArrayList<>();
        _cache_server = new ArrayList<>();
        _cache_sub = new ArrayList<>();
        return this;
    }

    public DatabaseManager saveDatabase() {



        return this;
    }

    public Server getServer(int serverId) {
        for (Server server: _cache_server) {
            if (server.getId() == serverId) {
                return server;
            }
        }

        //CHECK IN DB

        Server server = new Server(serverId);
        _cache_server.add(server);
        return server;
    }

    public ArrayList<Server> getAllServers() {
        return _cache_server;
    }

    public Pr0User getPr0User(String userName) {
        for (Pr0User pr0User: _cache_pr0Users) {
            if (pr0User.getUsername().equalsIgnoreCase(userName)) {
                return pr0User;
            }
        }

        //CHECK IN DB

        Pr0User pr0User = new Pr0User(userName);
        _cache_pr0Users.add(pr0User);
        return pr0User;
    }

    public ArrayList<Pr0User> getAllPr0Users() {
        return _cache_pr0Users;
    }

    public DatabaseManager addSub(int serverId, int channelId, String pr0UserName) {
        for (Sub sub: _cache_sub) {
            if(sub.getServer().getId() == serverId && sub.getPr0User().getUsername().equalsIgnoreCase(pr0UserName) && sub.getChannelId() == channelId) {
                return this;
            }
        }
        _cache_sub.add(new Sub(getServer(serverId), getPr0User(pr0UserName), channelId));
        return this;
    }

    public DatabaseManager addSub(Server server, int channelId, Pr0User pr0User) {

        // CHECK IN DB

        _cache_sub.add(new Sub(server, pr0User, channelId));
        return this;
    }

    public DatabaseManager removeSub(Sub sub) {
        if (_cache_sub.contains(sub)) {
            _cache_sub.remove(sub);
        }
        return this;
    }

    public ArrayList<Sub> getAllSubs() {
        return _cache_sub;
    }

}
