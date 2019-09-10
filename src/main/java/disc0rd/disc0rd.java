package disc0rd;

import io.sentry.Sentry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import disc0rd.events.MessageListener;

public class disc0rd {

    private static JDA _jda;

    public static JDA getJDA() {
        return _jda;
    }

    public static void main(String... args) {
        System.out.println("disc0rd - Community Bot - by scolastico");
        System.out.println("     _ _           ___          _ \n" +
                "  __| (_)___  ___ / _ \\ _ __ __| |\n" +
                " / _` | / __|/ __| | | | '__/ _` |\n" +
                "| (_| | \\__ \\ (__| |_| | | | (_| |\n" +
                " \\__,_|_|___/\\___|\\___/|_|  \\__,_|\n" +
                "                                  ");
        try {
            System.out.println("Version " + VersionController.getVersion() + " | Commit " + VersionController.getCommit() + " | Time " + VersionController.getTime());
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

        Sentry.init("https://eb8a6dbb31f247a1b57ab386ef1a88b7@sentry.io/1645265?async.shutdowntimeout=15000&async.gracefulshutdown=true");


        try {

            Sentry.getContext().addTag("version", "v" + VersionController.getVersion() + "c" + VersionController.getCommit());

            try {



            } catch (Exception error) {

                System.out.println("Database Error Error:");
                error.printStackTrace();
                System.out.println("[info] Sending use statistics...");
                Sentry.getContext().addTag("error lvl", "DB-ERROR");
                Sentry.capture(error);
                return;

            }

            JDA jda = new JDABuilder(args[0]).build();
            jda.addEventListener(new MessageListener());

        } catch (Exception error) {


            System.out.println("Error:");
            error.printStackTrace();
            System.out.println("[info] Sending use statistics...");
            Sentry.getContext().addTag("error lvl", "FATAL");
            Sentry.capture(error);
            return;
        }

    }

}
