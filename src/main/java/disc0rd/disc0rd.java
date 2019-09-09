package disc0rd;

import disc0rd.sql.MysqlConnector;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import disc0rd.events.MessageListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.sql.SQLException;

public class disc0rd {

    private static SentryClient _sentry;
    private static JDA _jda;

    public static SentryClient getSentryClient() {
        return _sentry;
    }

    public static JDA getJDA() {
        return _jda;
    }

    public static void main(String... args) {
        System.out.println("disc0rd - Community Bot");
        System.out.println("     _ _           ___          _ \n" +
                "  __| (_)___  ___ / _ \\ _ __ __| |\n" +
                " / _` | / __|/ __| | | | '__/ _` |\n" +
                "| (_| | \\__ \\ (__| |_| | | | (_| |\n" +
                " \\__,_|_|___/\\___|\\___/|_|  \\__,_|\n" +
                "                                  ");
        try {
            System.out.println("Version " + VersionController.getVersion() + " | Commit " + VersionController.getCommit() + " | Time " + VersionController.getTime() + " | by scolastico");
        } catch (Exception e) {
            System.out.println("[ERROR] This build is corrupt!");
            return;
        }
        System.out.println("Open Source on GitHub: https://go.scolasti.co/disc0rd_github");
        System.out.println("------------------------------------------------------------");

        if (args.length == 0) {
            System.out.println("Need argument: [discord_bot_token]");
            return;
        }

        Sentry.init("https://eb8a6dbb31f247a1b57ab386ef1a88b7@sentry.io/1645265");
        _sentry = SentryClientFactory.sentryClient();

        try {

            _sentry.setRelease( new VersionController().versionInformation());

            try {

                MysqlConnector connector = MysqlConnector.setInstance("jdbc:sqlite:./database.sqlite");

            } catch (SQLException error) {

                System.out.println("SQL Error:");
                System.out.println(error.getMessage());
                return;

            }

            JDA jda = new JDABuilder(args[0]).build();
            jda.addEventListener(new MessageListener());

        } catch (Exception error) {
            _sentry.getContext().addTag("error lvl", "FATAL");
            Sentry.capture(error);
            System.out.println("Error:");
            System.out.println(error.getMessage());
            return;
        }

    }

}
