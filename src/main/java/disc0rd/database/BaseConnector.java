package disc0rd.database;

import disc0rd.config.Settings;
import io.sentry.Sentry;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseConnector {

    private static BaseConnector _instance = null;

    private Connection _connection;

    private BaseConnector() {

        try {

            Settings settings = Settings.getInstance();

            _connection = DriverManager.getConnection("jdbc:mysql://" + settings.getString("mysql.host") + ":" + settings.getString("mysql.port") + "/"  + settings.getString("mysql.database"), settings.getString("mysql.user"), settings.getString("mysql.password"));

        } catch (Exception error) {

            HandleSqlError(error);

        }
    }

    public static BaseConnector getInstance() {
        if(_instance == null) {
            _instance = new BaseConnector();
        }
        return _instance;
    }

    public Connection getConnection() {
        return _connection;
    }

    public static void HandleSqlError(Exception error) {
        System.out.println("Database Error:");
        error.printStackTrace();
        System.out.println("[info] Sending use statistics...");
        Sentry.getContext().addTag("type", "DB-ERROR");
        Sentry.capture(error);
        Sentry.close();
        System.exit(1);
    }

}
