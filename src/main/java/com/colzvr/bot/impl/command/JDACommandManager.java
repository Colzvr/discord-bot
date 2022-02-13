package com.colzvr.bot.impl.command;

import com.colzvr.bot.impl.command.executor.JDASlashCommandExecutor;
import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.command.RegisteredCommand;
import com.wizardlybump17.wlib.command.holder.Command;
import net.dv8tion.jda.api.JDA;

import java.util.Set;

public class JDACommandManager extends CommandManager {

    public JDACommandManager(JDACommandHolder holder) {
        super(holder, (sender, command) -> {
            Command jdaCommand = holder.getCommand(command.getName());
            String[] options = command.getCommand().options();

            for (String option : options)
                if (option.equalsIgnoreCase("slash") && !(jdaCommand.getExecutor() instanceof JDASlashCommandExecutor))
                    return false;

            return true;
        });
    }

    @Override
    public Set<RegisteredCommand> registerCommands(Object... objects) {
        Set<RegisteredCommand> commands = super.registerCommands(objects);

        for (RegisteredCommand command : commands) {
            for (String option : command.getCommand().options()) {
                if (!option.equalsIgnoreCase("slash"))
                    continue;

                String description = command.getCommand().description();
                if (description.isEmpty())
                    description = command.getCommand().execution();

                ((JDA) holder.getHandle()).upsertCommand(command.getName(), description).queue();
            }
        }

        return commands;
    }
}
