package com.colzvr.bot.impl.command.executor;

import com.colzvr.bot.impl.command.MemberContextSender;
import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.command.CommandSender;
import com.wizardlybump17.wlib.command.holder.CommandExecutor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class JDAPrefixCommandExecutor extends ListenerAdapter implements CommandExecutor {

    public static final String PREFIX = "!";

    private final CommandManager manager;

    public JDAPrefixCommandExecutor(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        User author = event.getAuthor();
        if (author.isBot())
            return;

        if (!event.getMessage().getContentRaw().startsWith(PREFIX))
            return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        String commandName = args[0].substring(PREFIX.length());
        try {
            execute(
                    new MemberContextSender(
                            event.getMember(),
                            event.getMessage(),
                            event.getTextChannel(),
                            event.getGuild(),
                            null
                    ),
                    commandName,
                    Arrays.copyOfRange(args, 1, args.length)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(CommandSender<?> sender, String commandName, String[] args) {
        String commandExecution = commandName + " " + String.join(" ", args);
        manager.execute(sender, commandExecution);
    }
}
