package disc0rd.events.cmds;

import disc0rd.Disc0rd;
import disc0rd.database.Module_DB_Sub;
import disc0rd.events.MessageListener;
import disc0rd.modules.Module_HttpClient;
import disc0rd.modules.Module_Settings;
import io.sentry.Sentry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Module_CMD_Sub {

    public static void runSubscribe(String[] args, MessageReceivedEvent event) {

        if (args.length != 2) {
            MessageListener.sendHelp(Disc0rd.getJDA(), event.getTextChannel());
        }

        String user = args[1];

        try {
            if (!event.getGuild().getMember(event.getMessage().getAuthor()).isOwner()) {
                if (Module_Settings.getSetting(event.getGuild(), "allowAdmin").equalsIgnoreCase("1")) {
                    if (!event.getGuild().getMember(event.getMessage().getAuthor()).hasPermission(Permission.ADMINISTRATOR)) {
                        MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
                        return;
                    }
                } else {
                    MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
                    return;
                }
            }
        } catch (Exception error) {
            MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
            return;
        }

        Module_DB_Sub.Sub sub = Module_DB_Sub.getInstance().getSub(event.getGuild().getIdLong(), event.getTextChannel().getIdLong(), user);

        if (sub != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Error!");
            embedBuilder.setDescription("the user `" + user + "` is already subscribed in this channel!");
            embedBuilder.setColor(new Color(238, 77, 46));
            event.getTextChannel().sendMessage(embedBuilder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);
        }

        try {
            String response = new Module_HttpClient().makeRequest("https://pr0gramm.com/api/items/get?user=" + user);
            JSONObject object = new JSONObject(response);
            if (object.getJSONArray("items").length() == 0) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Sorry,");
                embedBuilder.setDescription("the user `" + user + "` can't be found.\nThe user must already have a sfw upload!");
                embedBuilder.setColor(new Color(238, 77, 46));
                event.getTextChannel().sendMessage(embedBuilder.build()).queue();
                return;
            }
        } catch (Exception error) {
            MessageListener.sendErrorInfo(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
            Sentry.capture(error);
            System.out.println("[WARN] pr0gramm api error:");
            error.printStackTrace();
            return;
        }

        Module_DB_Sub.getInstance().addSub(event.getGuild().getIdLong(), event.getTextChannel().getIdLong(), user);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Success!");
        embedBuilder.setDescription("the user `" + user + "` is now in this channel subscribed!");
        embedBuilder.setColor(new Color(29, 185, 146));
        event.getTextChannel().sendMessage(embedBuilder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);

    }


    public static void runUnsubscribe(String[] args, MessageReceivedEvent event) {

        if (args.length != 2) {
            MessageListener.sendHelp(Disc0rd.getJDA(), event.getTextChannel());
        }

        String user = args[1];

        try {
            if (!event.getGuild().getMember(event.getMessage().getAuthor()).isOwner()) {
                if (Module_Settings.getSetting(event.getGuild(), "allowAdmin").equalsIgnoreCase("1")) {
                    if (!event.getGuild().getMember(event.getMessage().getAuthor()).hasPermission(Permission.ADMINISTRATOR)) {
                        MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
                        return;
                    }
                } else {
                    MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
                    return;
                }
            }
        } catch (Exception error) {
            MessageListener.sendNotAllowed(disc0rd.Disc0rd.getJDA(), event.getTextChannel());
            return;
        }

        Module_DB_Sub.Sub sub = Module_DB_Sub.getInstance().getSub(event.getGuild().getIdLong(), event.getTextChannel().getIdLong(), user);

        if (sub == null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Error!");
            embedBuilder.setDescription("the user `" + user + "` is not subscribed in this channel!");
            embedBuilder.setColor(new Color(238, 77, 46));
            event.getTextChannel().sendMessage(embedBuilder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);
        }

        Module_DB_Sub.getInstance().deleteSub(sub.getId());

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Success!");
        embedBuilder.setDescription("the user `" + user + "` is not longer subscribed in this channel!");
        embedBuilder.setColor(new Color(29, 185, 146));
        event.getTextChannel().sendMessage(embedBuilder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);

    }


    public static void runListSubs(String[] args, MessageReceivedEvent event) {



    }


    public static void runListAllSubs(String[] args, MessageReceivedEvent event) {



    }

}
