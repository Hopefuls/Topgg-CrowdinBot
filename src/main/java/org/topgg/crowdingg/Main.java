package org.topgg.crowdingg;

import io.github.daflamingfox.CommandBuilder;
import okhttp3.OkHttpClient;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;
import org.json.JSONArray;
import org.topgg.crowdingg.commands.StatsCommand;
import org.topgg.crowdingg.commands.TestCommand;
import org.topgg.crowdingg.commands.pagination.InteractionListener;
import org.topgg.crowdingg.utils.crowdin.Crowdin;

public class Main {

    public static DiscordApi api;
    public static OkHttpClient okHttpClient = new OkHttpClient();
    public static JSONArray crowdinSupportedLanguages;
    public static String crowdinBearerToken;

    public static void main(String[] args) {

        crowdinBearerToken = args[1];

        api = new DiscordApiBuilder().setAllIntents().setToken(args[0]).login().join();
        System.out.println(api.createBotInvite());
        FallbackLoggerConfiguration.setDebug(true);

        crowdinSupportedLanguages = Crowdin.getSupportedLanguagesInJSONArray();
        System.out.println("Supported languages loaded!");

        api.addMessageComponentCreateListener(new InteractionListener());

        // Add Interaction Listener to listen for Slash Commands
        // api.addInteractionCreateListener(new InteractListener());

        CommandBuilder builder = new CommandBuilder("!", api);
        builder.addCommand("stats", null, new StatsCommand(), "get stats", "stats");
        builder.addCommand("stinknotic", null, new TestCommand(), "test", "test");
        builder.build();


    }

}
