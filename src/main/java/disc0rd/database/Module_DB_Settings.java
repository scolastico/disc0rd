package disc0rd.database;

import disc0rd.config.Settings;
import disc0rd.events.MessageListener;

import java.sql.ResultSet;

public class Module_DB_Settings {

    private static Module_DB_Settings _instance = null;

    private Module_DB_Settings() {

        try {
            BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                        "CREATE TABLE IF NOT EXISTS `" + Settings.getInstance().getString("mysql.prefix") + "settings` (" +
                            "`id` int NOT NULL PRIMARY KEY auto_increment," +
                            "`serverId` int NOT NULL," +
                            "`allowAdmin` bit(1) NOT NULL DEFAULT `0`" +
                            ");"
            );
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }

    }

    public static Module_DB_Settings getInstance() {
        if(_instance == null) {
            _instance = new Module_DB_Settings();
        }
        return _instance;
    }

    public static ServerSettings getServer(long guildId) {

        try {
            ResultSet resultSet = BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM " + Settings.getInstance().getString("mysql.prefix") + "setting WHERE serverId=" + guildId + ";"
            );
            if (resultSet.next()) {
                return new ServerSettings(resultSet.getInt("id"), resultSet.getLong("id"), (resultSet.getInt("id") == 1));
            }
            String sql = "INSERT INTO `" + Settings.getInstance().getString("mysql.prefix") + "sub` VALUES (NULL, " + guildId + ", 0);";
            BaseConnector.getInstance().getConnection().createStatement().executeUpdate(sql);
            resultSet = BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM " + Settings.getInstance().getString("mysql.prefix") + "setting WHERE serverId=" + guildId + ";"
            );
            if (resultSet.next()) {
                return new ServerSettings(resultSet.getInt("id"), resultSet.getLong("id"), (resultSet.getInt("id") == 1));
            }
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }

        return null;

    }


    public static void setServer(ServerSettings server) {
        try {
            String sql = "UPDATE table_name SET allowAdmin=" + server.getAdminAllowed() + " WHERE id=" + server.getId() + ";";
            BaseConnector.getInstance().getConnection().createStatement().executeUpdate(sql);
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }
    }

    public static boolean getAdminAllowed(long guildId) {


        return false;
    }

    public static void setAdminAllowed(Long guildId, Boolean bool) {

        ServerSettings serverSettings = getServer(guildId);

        serverSettings.setAdminAllowed(bool);

        setServer(serverSettings);

    }

    public static class ServerSettings {

        private int _id;
        private long _guildId;
        private boolean _adminAllowed;

        private ServerSettings(int id, long guildId, boolean adminAllowed) {
            _id = id;
            _guildId = guildId;
            _adminAllowed = adminAllowed;
        }

        public int getId() {
            return _id;
        }

        public long getGuildId() {
            return _guildId;
        }

        public boolean getAdminAllowed() {
            return _adminAllowed;
        }

        public ServerSettings setAdminAllowed(Boolean bool) {
            _adminAllowed = bool;
            return this;
        }

    }

}
