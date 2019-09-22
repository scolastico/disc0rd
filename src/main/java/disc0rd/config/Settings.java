package disc0rd.config;

import io.sentry.Sentry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Settings {

    private SortedProperties _properties = new SortedProperties();

    private static Settings _instance = null;

    private Settings() {

        try {

            File file = new File("disc0rd.properties");

            if (!file.exists()) {

                _properties.setProperty("mysql.host", "localhost");
                _properties.setProperty("mysql.database", "disc0rd");
                _properties.setProperty("mysql.user", "disc0rd");
                _properties.setProperty("mysql.password", "very-secure-password");
                _properties.setProperty("mysql.prefix", "disc0rd_");
                _properties.setProperty("mysql.port", "3306");
                _properties.setProperty("discord.token", "token");
                _properties.setProperty("other.inviteUrl", "http://invite.disc0rd.me/");
                _properties.setProperty("other.checkPr0Time", "30");
                _properties.setProperty("pr0gramm.username", "disc0rdBot");
                _properties.setProperty("pr0gramm.passwort", "password");

                FileOutputStream stream = new FileOutputStream(file);

                _properties.store(stream, "Configuration File for Disc0rd Community Bot - Generated: ");

                stream.close();

                System.out.println("Properties file \"disc0rd.properties\" generated!");
                System.out.println("Please edit the config file!");
                System.out.println("Shutdown...");
                System.exit(0);

            }

            _properties = new SortedProperties();
            FileInputStream stream = new FileInputStream(file);
            _properties.load(stream);
            stream.close();

        } catch (Exception e) {

            System.out.println("Settings Error:");
            e.printStackTrace();
            System.out.println("[info] Sending use statistics...");
            Sentry.getContext().addTag("type", "SETTINGS-ERROR");
            Sentry.capture(e);
            Sentry.close();
            System.exit(1);

        }

    }

    public static Settings getInstance() {
        if (_instance == null) {
            _instance = new Settings();
        }
        return _instance;
    }

    public String getString(String path) {
        return _properties.getProperty(path, null);
    }

    public Object getObject(String path) {
        return _properties.get(path);
    }

}
