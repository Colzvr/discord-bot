package com.colzvr.bot.impl.command.executor;

import com.colzvr.bot.impl.command.MemberContextSender;
import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.command.CommandSender;
import com.wizardlybump17.wlib.command.holder.CommandExecutor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class JDASlashCommandExecutor extends ListenerAdapter implements CommandExecutor {

    private final CommandManager manager;

    public JDASlashCommandExecutor(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String[] split = event.getCommandString().split(" ");
        MemberContextSender sender = new MemberContextSender(
                event.getMember(),
                null,
                event.getTextChannel(),
                event.getGuild(),
                event
        );
        execute(
                sender,
                split[0],
                Arrays.copyOfRange(split, 1, split.length)
        );
    }

    @Override
    public void execute(CommandSender<?> sender, String commandName, String[] args) {
        String commandExecution = commandName + " " + String.join(" ", args);
        manager.execute(sender, commandExecution);
    }
}
