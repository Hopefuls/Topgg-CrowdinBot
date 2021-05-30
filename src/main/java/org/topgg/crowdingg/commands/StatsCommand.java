package org.topgg.crowdingg.commands;

import me.hopedev.commandhandler.Command;
import me.hopedev.commandhandler.CommandData;
import me.hopedev.commandhandler.CommandExecutor;
import me.hopedev.commandhandler.CommandMessage;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.JSONObject;
import org.topgg.crowdingg.Variables;
import org.topgg.crowdingg.utils.crowdin.Crowdin;
import org.topgg.crowdingg.utils.crowdin.ProjectType;
import org.topgg.crowdingg.utils.crowdin.entity.Language;
import org.topgg.crowdingg.utils.crowdin.entity.Project;

import java.awt.*;
import java.util.ArrayList;

public class StatsCommand implements CommandExecutor {

    public static void executeFor(CommandData data, ProjectType type) {


        CommandMessage message = data.getCommandMessage();
        Project project = Crowdin.getProjectFromId(type.projectId);

        StringBuilder sb = new StringBuilder();

        sb.append("Translation Progress:").append("\n");
        if (message.getArgs().contains("--words"))
            sb.append("- Amount of **words**\n");
        else
            sb.append("- Amount of **phrases**\n");

        sb.append("``(total/translated/approved)``\n\n");
        project.getProgress(type.projectId).forEach(projectProgress -> {
            sb.append(Language.getNameFromIdOnTargetLanguages(project, projectProgress.getLanguageId()));

            JSONObject langStats;
            if (message.getArgs().contains("--words"))
                langStats = projectProgress.getWordCount();
            else
                langStats = projectProgress.getPhrasesCount();

            int total = langStats.getInt("total");
            int translated = langStats.getInt("translated");
            int approved = langStats.getInt("approved");
            sb.append(" (**" + total + "**/**" + translated + "**/**" + approved + "**) ");

            if (total == translated && translated != approved)
                sb.append(Variables.EMOJI_PARTIALLY.value);
            else if (total == translated && translated == approved)
                sb.append(Variables.EMOJI_FINISHED.value);
            else
                sb.append(Variables.EMOJI_NOT_DONE.value);

            sb.append("\n");
        });
        sb.append("\n");
        sb.append("`--words` for word-count on translations\n");
        sb.append("`--phrases` for phrases-count on translations\n");
        sb.append("`--backend` for backend translation stats\n");
        sb.append("`--web` for web translation stats");
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle(project.getProjectName())
                .setColor(Color.BLACK);

        eb.setDescription(sb.toString());

        data.getChannel().sendMessage(eb);


    }

    @Override
    public void execute(CommandData data, ArrayList<Command> commands) {

        System.out.println("Received!");
        CommandMessage message = data.getCommandMessage();

        if (message.getArgs().contains("--backend"))
            executeFor(data, ProjectType.BACKEND);
        else
            executeFor(data, ProjectType.WEB);

    }

}

