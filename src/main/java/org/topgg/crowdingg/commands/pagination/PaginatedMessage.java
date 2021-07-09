package org.topgg.crowdingg.commands.pagination;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.topgg.crowdingg.Main;
import org.topgg.crowdingg.Variables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaginatedMessage {

    public static final HashMap<String /*messageid*/, PaginatedMessage /*Paginated Message*/> paginationCache = new HashMap<>();

    // TODO redo lol
    private final ServerTextChannel channel;
    public final String userID;
    public Message message;
    public int finalPage;
    public List<EmbedBuilder> pages = new ArrayList<>();
    public int currentPage;

    public PaginatedMessage(ServerTextChannel channel, String userID) {
        this.channel = channel;
        this.userID = userID;

    }

    public PaginatedMessage appendPage(EmbedBuilder eb) {
        pages.add(eb);
        return this;
    }

    public final void changePage(int page) {
        if (page < 0) {
            return;
        }
        if (page == finalPage) {
            return;
        }

        currentPage = page;
    }

    public final void createPagination() {

        MessageBuilder builder = new MessageBuilder();

        builder.setEmbed(pages.get(0).setFooter("Page " + (currentPage + 1) + " of " + (pages.size()) + " | Autodeletes after 5 minutes"));

        builder.addComponents(
                ActionRow.of(Button.primary("crdnbot-pageback", Variables.PAGINATION_LEFT.value),
                        Button.primary("crdnbot-pagenext", Variables.PAGINATION_RIGHT.value),
                        Button.danger("crdnbot-destroy", Main.api.getCustomEmojiById("862865801635430400").get())));

        this.message = builder.send(channel).join();
        this.currentPage = 0;
        this.finalPage = pages.size();

        paginationCache.put(message.getIdAsString(), this);
    }

    public void updatePage() {
        message.edit(pages.get(currentPage).setFooter("Page " + (currentPage + 1) + " of " + (pages.size()) + " | Autodeletes after 5 minutes"));
    }


}
