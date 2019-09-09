package disc0rd.events;

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

        JDA jda = disc0rd.disc0rd.getJDA();
        String msg = event.getMessage().getContentRaw();

        if (event.isFromType(ChannelType.PRIVATE) || event.isFromType(ChannelType.GROUP)) {



        } else if (event.isFromType(ChannelType.TEXT)) {

            if (msg.startsWith("disc0rd/")) {

                msg = msg.replaceFirst("disc0rd/", "");

                String[] args = msg.split(" ");

                try {
                    event.getMessage().delete().complete();
                } catch (Exception e) {}

                if (args.length == 0) {
                    sendHelp(jda, event.getTextChannel());
                } else if (args.length == 1) {

                    if (args[0].equalsIgnoreCase("help")) {

                        sendHelp(jda, event.getTextChannel());

                    } else if (args[0].equalsIgnoreCase("listSubs")) {



                    } else if (args[0].equalsIgnoreCase("listAllSubs")) {



                    } else if (args[0].equalsIgnoreCase("reset")) {



                    } else if (args[0].equalsIgnoreCase("allowAdmin")) {



                    } else {
                        sendHelp(jda, event.getTextChannel());
                    }
                }

            }

        }

    }

    private void sendHelp(JDA jda, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("disc0rd - Community Bot");
        embedBuilder.setColor(Color.GREEN);
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

}
