package com.colzvr.bot.listener;

import com.colzvr.bot.Main;
import com.colzvr.bot.impl.command.ArgsNodeWrapper;
import com.colzvr.bot.util.ArrayUtil;
import com.wizardlybump17.wlib.command.RegisteredCommand;
import com.wizardlybump17.wlib.command.args.ArgsNode;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BotListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        if (!event.getGuild().getId().equals(Main.COLZVR))
            event.getGuild().leave().queue();
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        Guild guild = event.getGuild();
        for (Command command : guild.retrieveCommands().complete())
            command.delete().complete();

        List<RegisteredCommand> commands = Main.getCommandManager().getCommands();

        Set<CommandData> commandsData = new HashSet<>();

        for (RegisteredCommand command : commands) {
            if (ArrayUtil.indexOf(command.getCommand().options(), "slash") == -1)
                continue;

            String description = command.getCommand().description();
            if (description.isEmpty())
                description = command.getCommand().execution();

            CommandDataImpl data = new CommandDataImpl(
                    command.getName(),
                    description
            );

            for (int i = 1; i < command.getNodes().size(); i++) {
                ArgsNode node = command.getNodes().get(i);
                data.addOptions(
                        new OptionData(
                                ArgsNodeWrapper.wrap(node.getReader() == null ? String.class : node.getReader().getType()),
                                node.getName(),
                                node.getDescription() == null ? node.getName() : node.getDescription(),
                                node.isRequired()
                        )
                );

                commandsData.add(data);
            }
        }

        guild.updateCommands().addCommands(commandsData).complete();
    }
}
