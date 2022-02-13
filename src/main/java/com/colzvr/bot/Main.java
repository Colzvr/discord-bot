package com.colzvr.bot;

import com.colzvr.bot.command.AdminCommands;
import com.colzvr.bot.impl.command.JDACommandHolder;
import com.colzvr.bot.impl.command.JDACommandManager;
import com.colzvr.bot.impl.command.executor.JDAPrefixCommandExecutor;
import com.colzvr.bot.impl.command.executor.JDASlashCommandExecutor;
import com.colzvr.bot.listener.BotListener;
import com.wizardlybump17.wlib.command.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(System.getenv("token")).build();

        CommandManager commandManager = new JDACommandManager(new JDACommandHolder(jda));
        commandManager.registerCommands(
                new AdminCommands()
        );

        jda.addEventListener(
                new JDAPrefixCommandExecutor(commandManager),
                new JDASlashCommandExecutor(commandManager),
                new BotListener()
        );

        jda.retrieveCommands().queue(commands -> {
            for (net.dv8tion.jda.api.interactions.commands.Command command : commands) {
                command.delete().queue();
            }
        });

        for (Guild guild : jda.getGuilds())
            if (!guild.getId().equals(BotListener.COLZVR))
                guild.leave().queue();
    }
}
