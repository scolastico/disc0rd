package disc0rd.sql;

import java.sql.*;

public class MysqlConnector {

    private static MysqlConnector instance;

    private Connection conn;

    private MysqlConnector(Connection connection) {
        conn = connection;
    }

    public static MysqlConnector getInstance() {
        return instance;
    }

    public static MysqlConnector setInstance(String url) throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        if (conn == null) {
            throw new SQLException("Connection dead?! How is this possible?");
        }
        instance = new MysqlConnector(conn);
        instance.runSQL(
                "-- CREATE DB " +
                        "SET FOREIGN_KEY_CHECKS = 0;\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS `server`\n" +
                        "(\n" +
                        " `id`        int NOT NULL AUTO_INCREMENT ,\n" +
                        " `server_id` int NOT NULL ,\n" +
                        " `subs`      int NOT NULL ,\n" +
                        "\n" +
                        "PRIMARY KEY (`id`),\n" +
                        "KEY `fkIdx_25` (`subs`),\n" +
                        "CONSTRAINT `FK_25` FOREIGN KEY `fkIdx_25` (`subs`) REFERENCES `subs` (`id`)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS `subs`\n" +
                        "(\n" +
                        " `id`         int NOT NULL AUTO_INCREMENT ,\n" +
                        " `server`     int NOT NULL ,\n" +
                        " `user`       int NOT NULL ,\n" +
                        " `channel_id` int NOT NULL ,\n" +
                        "\n" +
                        "PRIMARY KEY (`id`),\n" +
                        "KEY `fkIdx_22` (`server`),\n" +
                        "CONSTRAINT `FK_22` FOREIGN KEY `fkIdx_22` (`server`) REFERENCES `server` (`id`),\n" +
                        "KEY `fkIdx_28` (`user`),\n" +
                        "CONSTRAINT `FK_28` FOREIGN KEY `fkIdx_28` (`user`) REFERENCES `user` (`id`)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS `user`\n" +
                        "(\n" +
                        " `id`           int NOT NULL AUTO_INCREMENT ,\n" +
                        " `user_id`      int NOT NULL ,\n" +
                        " `subs`         int NOT NULL ,\n" +
                        " `last_post_id` int NOT NULL ,\n" +
                        "\n" +
                        "PRIMARY KEY (`id`),\n" +
                        "KEY `fkIdx_35` (`subs`),\n" +
                        "CONSTRAINT `FK_35` FOREIGN KEY `fkIdx_35` (`subs`) REFERENCES `subs` (`id`)\n" +
                        ");\n" +
                        "\n" +
                        "\n" +
                        "SET FOREIGN_KEY_CHECKS = 1;"
        );
        return instance;
    }

    public boolean isAlive() {
        try {
            if (conn == null || conn.isClosed()) {
                return false;
            }
        } catch (Exception e){return false;}
        return true;
    }
    
    public Connection getConnection() {
        return conn;
    }

    public void runSQL(String sql) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
    };

}
