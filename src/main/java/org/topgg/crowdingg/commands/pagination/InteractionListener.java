package org.topgg.crowdingg.commands.pagination;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.awt.*;

public class InteractionListener implements MessageComponentCreateListener {
    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();

        String[] params;
        String customId = messageComponentInteraction.getCustomId();

        if (!customId.startsWith("crdnbot")) {
            return;
        }
        params = customId.split("-");
        String action = params[1];

        if (!PaginatedMessage.paginationCache.containsKey(String.valueOf(messageComponentInteraction.getMessageId()))) {

            messageComponentInteraction.getMessage().ifPresent(message -> {
                if (message.getAuthor().isYourself()) {
                    MessageBuilder builder = new MessageBuilder();
                    builder.setEmbed(new EmbedBuilder().setColor(Color.RED).setDescription("This Message is not valid anymore, please retry the command."));

                    messageComponentInteraction.createImmediateResponder().setFlags(MessageFlag.EPHEMERAL).addEmbed(new EmbedBuilder().setColor(Color.RED).setDescription("This Message is not valid anymore, please retry the command.")).respond();
                    return;
                }
                return;
            });
        }
        PaginatedMessage message = PaginatedMessage.paginationCache.get(String.valueOf(messageComponentInteraction.getMessageId()));

        if (!String.valueOf(message.userID).equals(messageComponentInteraction.getUser().getIdAsString())) {
            messageComponentInteraction.createImmediateResponder().setFlags(MessageFlag.EPHEMERAL).addEmbed(new EmbedBuilder().setColor(Color.RED).setDescription("Only the Message creator can Interact with these!"));
            return;
        }
        switch (action) {
            case "pageback":
                message.changePage(message.currentPage - 1);
                message.updatePage();


                break;

            case "pagenext":
                message.changePage(message.currentPage + 1);
                message.updatePage();
                break;
            case "destroy":
                message.message.delete();
                break;
        }

        messageComponentInteraction.createImmediateResponder().respond();


    }
}
