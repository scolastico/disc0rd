package disc0rd;

import disc0rd.sql.MysqlConnector;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import disc0rd.events.MessageListener;

import java.sql.Connection;
import java.sql.DriverManager;
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

        if (args.length == 0) {
            System.out.println("Need argument: [discord_bot_token]");
            return;
        }

        Sentry.init();
        Sentry.init("https://eb8a6dbb31f247a1b57ab386ef1a88b7@sentry.io/1645265");
        _sentry = SentryClientFactory.sentryClient();

        try {

            try {

                MysqlConnector connector = MysqlConnector.setInstance("jdbc:sqlite:database.sqlite");

            } catch (SQLException error) {

                System.out.println("SQL Error:");
                System.out.println(error.getMessage());

            }

            JDA jda = new JDABuilder(args[0]).build();
            jda.addEventListener(new MessageListener());

        } catch (Exception error) {
            _sentry.getContext().addTag("error lvl", "FATAL");
            Sentry.capture(error);
            System.out.println("Error:");
            System.out.println(error.getMessage());
        }

    }

}
