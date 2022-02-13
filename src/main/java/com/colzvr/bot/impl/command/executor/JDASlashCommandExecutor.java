package com.colzvr.bot.impl.command.executor;

import com.colzvr.bot.impl.command.MemberContextSender;
import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.command.CommandSender;
import com.wizardlybump17.wlib.command.holder.CommandExecutor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class JDASlashCommandExecutor extends ListenerAdapter implements CommandExecutor {

    private final CommandManager manager;

    public JDASlashCommandExecutor(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        StringBuilder builder = new StringBuilder();
        for (OptionMapping option : event.getOptions())
            builder.append(option.getAsString()).append(" ");

        String[] args = builder.toString().split(" ");

        MemberContextSender sender = new MemberContextSender(
                event.getMember(),
                null,
                event.getTextChannel(),
                event.getGuild(),
                event
        );
        execute(
                sender,
                event.getName(),
                args
        );
    }

    @Override
    public void execute(CommandSender<?> sender, String commandName, String[] args) {
        String commandExecution = commandName + " " + String.join(" ", args);
        manager.execute(sender, commandExecution);
    }
}
