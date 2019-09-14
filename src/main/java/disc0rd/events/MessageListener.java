package disc0rd.events;

import disc0rd.Disc0rd;
import disc0rd.config.Settings;
import disc0rd.events.cmds.Module_CMD_Sub;
import io.sentry.Sentry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        JDA jda = Disc0rd.getJDA();
        String msg = event.getMessage().getContentRaw();

        if (event.isFromType(ChannelType.PRIVATE) || event.isFromType(ChannelType.GROUP)) {

            try {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(new Color(238, 77, 46));
                embedBuilder.setTitle("Sorry,");
                embedBuilder.setDescription(
                                "im only available on servers... There you can write me with `disc0rd/help` to get an help!\n" +
                                "If you want me on your server click on following link: " + Settings.getInstance().getString("other.inviteUrl")
                );
                event.getChannel().sendMessage(embedBuilder.build()).queue();
                event.getMessage().delete().queue();
            } catch (Exception e) {
                Sentry.capture(e);
            }

        } else if (event.isFromType(ChannelType.TEXT)) {

            if (msg.startsWith("disc0rd/")) {

                msg = msg.replaceFirst("disc0rd/", "");
                String[] args = msg.split(" ");

                try {
                    event.getMessage().delete().complete();
                } catch (Exception e) {}

                if (args.length == 0) {
                    sendHelp(jda, event.getTextChannel());

                } else {

                    if (args[0].equalsIgnoreCase("help")) {
                        sendHelp(jda, event.getTextChannel());
                    } else if (args[0].equalsIgnoreCase("subscribe")) {
                        Module_CMD_Sub.runSubscribe(args, event);
                    } else if (args[0].equalsIgnoreCase("unsubscribe")) {
                        Module_CMD_Sub.runUnsubscribe(args, event);
                    } else if (args[0].equalsIgnoreCase("listSubs")) {
                        Module_CMD_Sub.runListSubs(args, event);
                    } else if (args[0].equalsIgnoreCase("listAllSubs")) {
                        Module_CMD_Sub.runListAllSubs(args, event);
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        //RESET
                    } else {
                        sendHelp(jda, event.getTextChannel());
                    }

                }

            }

        }

    }

    public static void sendHelp(JDA jda, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("disc0rd - Community Bot");
        embedBuilder.setColor(new Color(29, 185, 146));
        embedBuilder.addField(
                "Available Commands",
                "`disc0rd/help` - Shows this help!\n" +
                        "`disc0rd/subscribe` <username> - Subscribes an user in this channel.\n" +
                        "`disc0rd/unsubscribe` <username> - Unsubscribe an user in this channel.\n" +
                        "`disc0rd/listSubs` - Lists the subs of this channel.\n" +
                        "`disc0rd/listAllSubs` - Lists all subs of this server.\n" +
                        "`disc0rd/allowAdmin` <true/false> - Allow admins control this bot.\n" +
                        "`disc0rd/reset` - Resets this Server.",
                true
        );
        channel.sendMessage(embedBuilder.build()).queue();
    }

    public static void sendNotAllowed(JDA jda, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Sorry,");
        embedBuilder.setColor(new Color(238, 77, 46));
        embedBuilder.setDescription(
                "you are not allow'd to use the bot!\n" +
                        "Only the owner and if its enabled the admins can use this bot!"
        );
        channel.sendMessage(embedBuilder.build()).queue();
    }

    public static void sendErrorInfo(JDA jda, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Sorry,");
        embedBuilder.setColor(new Color(238, 77, 46));
        embedBuilder.setDescription(
                "There was an internal error of the bot. Pls try again or send us a message to `support@disc0rd.me`"
        );
        channel.sendMessage(embedBuilder.build()).queue();
    }

}
