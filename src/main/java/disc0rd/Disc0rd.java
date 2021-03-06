package disc0rd;

import disc0rd.config.Settings;
import disc0rd.database.BaseConnector;
import disc0rd.modules.Module_Sub;
import io.sentry.Sentry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import disc0rd.events.MessageListener;

import java.util.Timer;
import java.util.TimerTask;

public class Disc0rd {

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
            System.out.println("Versions - Code: " + VersionController.getVersionsCode());
        } catch (Exception e) {
            System.out.println("[ERROR] This build is corrupt!");
            System.exit(1);
            return;
        }
        System.out.println("Open Source on GitHub: https://github.com/scolastico/disc0rd");
        System.out.println("------------------------------------------------------------");

        Sentry.init("https://eb8a6dbb31f247a1b57ab386ef1a88b7@sentry.io/1645265?async.shutdowntimeout=15000&async.gracefulshutdown=true&stacktrace.app.packages=com.scolastico.disc0rd");

        try {

            Sentry.getContext().addTag("version", VersionController.getVersionsCode());

            Settings settings = Settings.getInstance();

            BaseConnector.getInstance();

            JDA jda = new JDABuilder((String) settings.getObject("discord.token")).build();
            jda.addEventListener(new MessageListener());
            _jda = jda;

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Module_Sub.runCheck(jda);
                }
            };
            timer.scheduleAtFixedRate(task,0,Integer.parseInt(settings.getString("other.checkPr0Time"))*1000);

        } catch (Exception error) {


            System.out.println("Error:");
            error.printStackTrace();
            System.out.println("[info] Sending use statistics...");
            Sentry.getContext().addTag("type", "FATAL");
            Sentry.capture(error);
            Sentry.close();
            System.exit(1);
            return;

        }

    }

}
