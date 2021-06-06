package org.topgg.crowdingg.utils.crowdin;

import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.util.concurrent.ExecutionException;

public class ConditionedAction {


    public static boolean dmUser(User user, EmbedBuilder eb) {
        try {
            user.sendMessage(eb).get();
            return true;
        } catch (InterruptedException | ExecutionException ex) {
            return false;
        }
    }

    public static boolean dmUser(User user, String message) {
        try {
            user.sendMessage(message).get();
            return true;
        } catch (InterruptedException | ExecutionException ex) {
            return false;
        }
    }

    public static void removeReaction(Reaction reaction, User user) {
        try {
            reaction.removeUser(user).get();
        } catch (InterruptedException | ExecutionException ignored) {

        }
    }
}
