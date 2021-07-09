package org.topgg.crowdingg.commands;

import io.github.daflamingfox.Command;
import io.github.daflamingfox.CommandData;
import io.github.daflamingfox.IExecutor;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.topgg.crowdingg.Variables;

import java.util.ArrayList;

public class TestCommand implements IExecutor {
    @Override
    public void execute(CommandData data, ArrayList<Command> commands) {
        ServerTextChannel channel = data.getChannel();


        MessageBuilder builder = new MessageBuilder();

        builder.setEmbed(new EmbedBuilder().setDescription("Test"));

        builder.addComponents(
                ActionRow.of(Button.primary("uhohstinky", Variables.PAGINATION_LEFT.value),
                        Button.primary("uhohstinky2", Variables.PAGINATION_RIGHT.value)));
        builder.send(channel);
    }
}
