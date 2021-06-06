package org.topgg.crowdingg.commands.pagination;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.topgg.crowdingg.Variables;
import org.topgg.crowdingg.utils.crowdin.ConditionedAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaginatedMessage {

    private static final HashMap<String /*messageid*/, PaginatedMessage /*Paginated Message*/> paginationCache = new HashMap<>();

    // TODO redo lol
    private final ServerTextChannel channel;
    private final String userID;
    public Message message;
    public int finalPage;
    public List<EmbedBuilder> pages = new ArrayList<>();
    private int currentPage;

    public PaginatedMessage(ServerTextChannel channel, String userID) {
        this.channel = channel;
        this.userID = userID;

    }

    public PaginatedMessage appendPage(EmbedBuilder eb) {
        pages.add(eb);
        return this;
    }

    public final void createPagination() {
        this.message = channel.sendMessage(pages.get(0).setFooter("Page " + (currentPage + 1) + " of " + (pages.size()) + " | Autodeletes after 5 minutes")).join();
        this.currentPage = 0;
        this.finalPage = pages.size();
        message.addReaction(Variables.PAGINATION_LEFT.value).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

        message.addReaction(Variables.PAGINATION_RIGHT.value).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

        this.message.addReactionAddListener(event -> {
            if (!event.getMessage().get().getIdAsString().equals(message.getIdAsString())) {
                return;
            }
            if (event.getUser().get().isYourself()) {
                return;
            }

            if (!event.getUser().get().getIdAsString().equals(this.userID)) {
                return;
            }

            ConditionedAction.removeReaction(event.getReaction().get(), event.getUser().get());
            if (!event.getUser().get().isYourself()) {
                if (event.getEmoji().equalsEmoji(Variables.PAGINATION_LEFT.value)) {
                    System.out.println("pageleft page is " + currentPage);
                    if (currentPage != 0) {
                        this.currentPage--;
                        updatePage();
                    }
                } else if (event.getEmoji().equalsEmoji(Variables.PAGINATION_RIGHT.value)) {
                    System.out.println("pageright page is " + currentPage);
                    if (currentPage != pages.size() - 1) {
                        this.currentPage++;
                        updatePage();

                    }
                }
            }

        });
    }

    public void updatePage() {
        message.edit(pages.get(currentPage).setFooter("Page " + (currentPage + 1) + " of " + (pages.size()) + " | Autodeletes after 5 minutes"));
    }


}
