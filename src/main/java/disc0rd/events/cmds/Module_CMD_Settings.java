package disc0rd.events.cmds;

import disc0rd.Disc0rd;
import disc0rd.database.Module_DB_Settings;
import disc0rd.events.MessageListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Module_CMD_Settings {

    public static void runAllowAdmin(String[] args, MessageReceivedEvent event) {

        if (args.length != 1) {
            MessageListener.sendHelp(Disc0rd.getJDA(), event.getTextChannel());
        }

        try {
            if (!event.getGuild().getMember(event.getMessage().getAuthor()).isOwner()) {
                MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
                return;
            }
        } catch (Exception error) {
            MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
            return;
        }

        Boolean bool = (!Module_DB_Settings.getAdminAllowed(event.getGuild().getIdLong()));

        Module_DB_Settings.setAdminAllowed(event.getGuild().getIdLong(), bool);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Success!");
        if (bool) {
            embedBuilder.setDescription("Admins have now the rights to use the bot!");
        } else {
            embedBuilder.setDescription("Admins have no rights to use the bot anymore!");
        }
        embedBuilder.setColor(new Color(29, 185, 146));
        event.getTextChannel().sendMessage(embedBuilder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);

    }

}
