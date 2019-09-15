package disc0rd.modules;

import disc0rd.database.Module_DB_Sub;
import io.sentry.Sentry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class Module_Sub {

    private static int _lastId = 0;

    public static void runCheck(JDA jda) {

        try {
            String response = new Module_HttpClient().makeRequest("https://pr0gramm.com/api/items/get");
            JSONObject object = new JSONObject(response);
            if (_lastId == 0) {
                _lastId = object.getJSONArray("items").getJSONObject(0).getInt("id");
                return;
            }
            for (int id = 0; object.getJSONArray("items").getJSONObject(id).getInt("id") > _lastId; id++) {
                String user = object.getJSONArray("items").getJSONObject(id).getString("user");
                Long post_id = object.getJSONArray("items").getJSONObject(id).getLong("id");
                String thump = object.getJSONArray("items").getJSONObject(id).getString("thumb");

                ArrayList<Module_DB_Sub.Sub> subs = Module_DB_Sub.getInstance().getSub(user);
                for (Module_DB_Sub.Sub sub:subs) {
                    try {
                        Guild guild = jda.getGuildById(sub.getServerId());
                        TextChannel channel = guild.getTextChannelById(sub.getChannelId());
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("New Upload!","https://pr0gramm.com/new/" +  post_id);
                        embedBuilder.setThumbnail("https://thumb.pr0gramm.com/" + thump);
                        embedBuilder.setAuthor(user, "https://pr0gramm.com/user/" + user);
                        embedBuilder.setColor(new Color(238, 77, 46));
                        channel.sendMessage(embedBuilder.build()).queue();
                    } catch (Exception e) {
                        Module_DB_Sub.getInstance().deleteSub(sub.getId());
                    }
                }
            }
            _lastId = object.getJSONArray("items").getJSONObject(0).getInt("id");
        } catch (Exception error) {
            Sentry.capture(error);
            System.out.println("[WARN] pr0gramm api error:");
            error.printStackTrace();
        }

    }

}
