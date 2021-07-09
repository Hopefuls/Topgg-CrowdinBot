package org.topgg.crowdingg.commands;


import io.github.daflamingfox.Command;
import io.github.daflamingfox.CommandData;
import io.github.daflamingfox.CommandMessage;
import io.github.daflamingfox.IExecutor;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.json.JSONObject;
import org.topgg.crowdingg.Main;
import org.topgg.crowdingg.Variables;
import org.topgg.crowdingg.commands.pagination.PaginatedMessage;
import org.topgg.crowdingg.utils.crowdin.Crowdin;
import org.topgg.crowdingg.utils.crowdin.FlagParser;
import org.topgg.crowdingg.utils.crowdin.ProjectType;
import org.topgg.crowdingg.utils.crowdin.entity.Language;
import org.topgg.crowdingg.utils.crowdin.entity.Project;
import org.topgg.crowdingg.utils.crowdin.entity.ProjectProgress;
import org.topgg.crowdingg.utils.crowdin.enums.DisplayType;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StatsCommand implements IExecutor {

    public static Message executeFor(CommandData data, ProjectType type, DisplayType displayType) {

        CommandMessage message = data.getCommandMessage();
        Project project = Crowdin.getProjectFromId(type.projectId);
        ArrayList<ProjectProgress> progress = project.getProgress(type.projectId);


        // sb.append("(Click on a language/flag to go to the corresponding language on crowdin)\n\n");
        // Fuck you discord embed character limit, kinda ruined this one sadge

        if (displayType.equals(DisplayType.FULL)) {

            int langPerPage = 6;
            int tempCurrLangCount = 0;

            PaginatedMessage paginatedMessage = new PaginatedMessage(data.getChannel(), data.getMessageAuthor().getIdAsString());

            /* Layout FULL
            (Project Title)

            Translation Progress
            - Amount of (filter type, words/phrases)
            `(total/translated/approved)`

            Language1 (x/x/x) emoji
            Language2 (x/x/x) emoji
            Language3 (x/x/x) emoji
            Language4 (x/x/x) emoji
            Language5 (x/x/x) emoji
            Language6 (x/x/x) emoji

            --words for word-count on translations
            --phrases for phrases-count on translations
            --backend for backend translation stats
            --web for web translation stats

            (( PAGINATION ))
             */


            StringBuilder sb = new StringBuilder();

            sb.append("Translation Progress:").append("\n");
            if (message.getArgs().contains("--words"))
                sb.append("- Amount of **words**\n");
            else
                sb.append("- Amount of **phrases**\n");

            sb.append("``(total/translated/approved)``\n\n");

            for (int i = 1; i < progress.size(); i++) {

                ProjectProgress projectProgress = progress.get(i - 1);
                sb.append(Language.getNameFromIdOnTargetLanguages(project, projectProgress.getLanguageId()));

                JSONObject langStats;
                if (message.getArgs().contains("--words"))
                    langStats = projectProgress.getWordCount();
                else
                    langStats = projectProgress.getPhrasesCount();

                int total = langStats.getInt("total");
                int translated = langStats.getInt("translated");
                int approved = langStats.getInt("approved");

                sb.append("(**" + total + "**/**" + translated + "**/**" + approved + "**)");

                if (total == translated && translated != approved)
                    sb.append(Variables.EMOJI_PARTIALLY.value);
                else if (total == translated)
                    sb.append(Variables.EMOJI_FINISHED.value);
                else
                    sb.append(Variables.EMOJI_NOT_DONE.value);

                sb.append("\n");

                tempCurrLangCount++;

                if (tempCurrLangCount == langPerPage) {
                    System.out.println("Append");
                    sb.append("\n");
                    sb.append("`--words` for word-count on translations\n");
                    sb.append("`--phrases` for phrases-count on translations\n");
                    sb.append("`--backend` for backend translation stats\n");
                    sb.append("`--web` for web translation stats");

                    EmbedBuilder eb = new EmbedBuilder()
                            .setTitle(project.getProjectName())
                            .setColor(Color.BLACK);

                    eb.setDescription(sb.toString());
                    eb.setUrl("https://crowdin.com/project/" + project.getProjectIdentifier());

                    paginatedMessage.appendPage(eb);
                    tempCurrLangCount = 0;

                    sb = new StringBuilder();

                    sb.append("Translation Progress:").append("\n");
                    if (message.getArgs().contains("--words"))
                        sb.append("- Amount of **words**\n");
                    else
                        sb.append("- Amount of **phrases**\n");

                    sb.append("``(total/translated/approved)``\n\n");

                }


            }

            paginatedMessage.createPagination();

            return paginatedMessage.message;

        } else {

            /* Layout SHORT
            (Project Title)

            Translation Progress:

            // ROWS
            (Flag1 emoji) (Flag2 emoji) (Flag3 emoji) (Flag4 emoji)
            // 4 COLUMNS, REST IN END

            emoji finished
            emoji not finished
            --backend for backend translation stats
            --web for web translation stats
            --full for a full paginated overview of translations

            (( NO PAGINATION ))
             */
            StringBuilder sb = new StringBuilder();
            sb.append("Translation Progress:").append("\n");

            final int[] rowCount = {0};
            progress.forEach(projectProgress -> {
                String flagEmoji = FlagParser.getByCode(projectProgress.getLanguageId());

                sb.append("(");
                sb.append(flagEmoji);

                JSONObject langStats = projectProgress.getPhrasesCount();

                int total = langStats.getInt("total");
                int translated = langStats.getInt("translated");
                int approved = langStats.getInt("approved");

                sb.append(" ");

                if (total == translated && translated == approved)
                    sb.append(Variables.EMOJI_FINISHED.value);
                else
                    sb.append(Variables.EMOJI_NOT_DONE.value);

                sb.append(") ");
                rowCount[0]++;
                if (rowCount[0] % 4 == 0) {
                    sb.append("\n");
                }
            });

            sb.append("\n\n");

            // Legend
            sb.append(Variables.EMOJI_FINISHED.value).append(" finished\n");
            sb.append(Variables.EMOJI_NOT_DONE.value).append(" not finished\n\n");

            // Filters
            sb.append("`--backend` for backend translation stats\n");
            sb.append("`--web` for web translation stats\n");
            sb.append("`--full` for a full paginated overview of translations\n");

            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle(project.getProjectName())
                    .setColor(Color.BLACK)
                    .setFooter("Autodeletes after 5 minutes");

            eb.setDescription(sb.toString());

            eb.setUrl("https://crowdin.com/project/" + project.getProjectIdentifier());


            return data.getChannel().sendMessage(eb).exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            }).join();

        }


        // sb.append("\n`--full` for a more detailed list of translations");
    }


    public void execute(CommandData data, ArrayList<Command> commands) {

        System.out.println("Received!");
        CommandMessage message = data.getCommandMessage();


        Role translatorRole = Main.api.getRoleById(Variables.ROLE_ID_TRANSLATOR.value).get(); // Translator Role

        if (!data.getUser().getRoles(data.getEvent().getServer().get()).contains(translatorRole))
            return;

        Message messageResponse;
        if (message.getArgs().contains("--full")) {
            messageResponse = executeFor(data, message.getArgs().contains("--backend") ?
                    ProjectType.BACKEND :
                    ProjectType.WEB, DisplayType.FULL);
        } else {
            messageResponse = executeFor(data, message.getArgs().contains("--backend") ?
                    ProjectType.BACKEND :
                    ProjectType.WEB, DisplayType.SHORT);
        }

        messageResponse.getApi().getThreadPool().getDaemonScheduler().schedule(() -> {
            PaginatedMessage.paginationCache.remove(messageResponse.getIdAsString());
            messageResponse.delete("Autodeletion after 5 Minutes.");
        }, 5, TimeUnit.MINUTES);


        // executeFor(data, ProjectType.WEB);

    }

}

