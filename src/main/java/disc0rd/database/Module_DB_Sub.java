package disc0rd.database;

import disc0rd.config.Settings;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Module_DB_Sub {

    private static Module_DB_Sub _instance = null;

    private Module_DB_Sub() {

        try {
            BaseConnector.getInstance().getConnection().createStatement().execute(
                        "CREATE TABLE IF NOT EXISTS `" + Settings.getInstance().getString("mysql.prefix") + "sub` (" +
                            "`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                            "`server` BIGINT NOT NULL," +
                            "`channel` BIGINT NOT NULL," +
                            "`pr0user` varchar(255) NOT NULL" +
                            ");"
            );
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }

    }

    public static Module_DB_Sub getInstance() {
        if(_instance == null) {
            _instance = new Module_DB_Sub();
        }
        return _instance;
    }

    public void addSub(long serverId, long channelId, String pr0user) {
        try {
            String sql = "INSERT INTO `" + Settings.getInstance().getString("mysql.prefix") + "sub` VALUES (NULL, " + serverId + ", " + channelId + ", '" + pr0user + "');";
            BaseConnector.getInstance().getConnection().createStatement().executeUpdate(sql);
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
    }

    public Sub getSub(int id) {
        try {
            ResultSet resultSet = BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM " + Settings.getInstance().getString("mysql.prefix") + "sub WHERE id=" + id + ";"
            );
            if (resultSet.next()) {
                return new Sub(resultSet.getInt("id"), resultSet.getLong("server"), resultSet.getLong("channel"), resultSet.getString("pr0user"));
            }
            return null;
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
        return null;
    }

    public ArrayList<Sub> getSub(long serverId) {
        try {
            ResultSet resultSet = BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM " + Settings.getInstance().getString("mysql.prefix") + "sub WHERE server=" + serverId
            );
            ArrayList<Sub> subs = new ArrayList<>();
            while (resultSet.next()) {
                subs.add(new Sub(resultSet.getInt("id"), resultSet.getLong("server"), resultSet.getLong("channel"), resultSet.getString("pr0user")));
            }
            return subs;
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
        return null;
    }

    public ArrayList<Sub> getSub(long serverId, long channelId) {
        try {
            ResultSet resultSet = BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM " + Settings.getInstance().getString("mysql.prefix") + "sub WHERE server=" + serverId + " AND channel=" + channelId + ";"
            );
            ArrayList<Sub> subs = new ArrayList<>();
            while (resultSet.next()) {
                subs.add(new Sub(resultSet.getInt("id"), resultSet.getLong("server"), resultSet.getLong("channel"), resultSet.getString("pr0user")));
            }
            return subs;
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
        return null;
    }


    public Sub getSub(long serverId, long channelId, String pr0user) {
        try {
            ResultSet resultSet = BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM " + Settings.getInstance().getString("mysql.prefix") + "sub WHERE server=" + serverId + " AND channel=" + channelId + " AND pr0user='" + pr0user + "';"
            );
            if (resultSet.next()) {
                return new Sub(resultSet.getInt("id"), resultSet.getLong("server"), resultSet.getLong("channel"), resultSet.getString("pr0user"));
            }
            return null;
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
        return null;
    }


    public ArrayList<Sub> getSub(String pr0User) {
        try {
            ResultSet resultSet = BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM " + Settings.getInstance().getString("mysql.prefix") + "sub WHERE pr0user='" + pr0User + "';"
            );
            ArrayList<Sub> subs = new ArrayList<>();
            while (resultSet.next()) {
                subs.add(new Sub(resultSet.getInt("id"), resultSet.getLong("server"), resultSet.getLong("channel"), resultSet.getString("pr0user")));
            }
            return subs;
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
        return null;
    }

    public void deleteSub(int id) {
        try {
            BaseConnector.getInstance().getConnection().createStatement().executeUpdate(
                    "DELETE FROM " + Settings.getInstance().getString("mysql.prefix") + "sub WHERE id=" + id + ";"
            );
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
    }

    public static class Sub {

        private int _id = 0;
        private long _serverId = 0;
        private long _channelId = 0;
        private String _pr0user = "";

        private Sub(int id, long serverId, long channelId, String pr0user) {
            _id = id;
            _serverId = serverId;
            _channelId = channelId;
            _pr0user = pr0user;
        }

        public int getId() {
            return _id;
        }

        public long getServerId() {
            return _serverId;
        }

        public long getChannelId() {
            return _channelId;
        }

        public String getPr0user() {
            return _pr0user;
        }

    }

}
