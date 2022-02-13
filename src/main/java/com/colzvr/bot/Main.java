package com.colzvr.bot;

import com.colzvr.bot.command.AdminCommands;
import com.colzvr.bot.impl.command.JDACommandHolder;
import com.colzvr.bot.impl.command.executor.JDAPrefixCommandExecutor;
import com.colzvr.bot.impl.command.executor.JDASlashCommandExecutor;
import com.colzvr.bot.listener.BotListener;
import com.wizardlybump17.wlib.command.CommandManager;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import javax.security.auth.login.LoginException;

public class Main {

    public static final String COLZVR = "807386597690966038";

    @Getter
    private static JDA jda;
    @Getter
    private static CommandManager commandManager;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault(System.getenv("token")).build();

        initCommandManager();
        leaveGuilds();
        registerListeners();
    }

    private static void leaveGuilds() {
        for (Guild guild : jda.getGuilds())
            if (!guild.getId().equals(COLZVR))
                guild.leave().complete();
    }

    private static void initCommandManager() {
        commandManager = new CommandManager(new JDACommandHolder(jda));

        commandManager.registerCommands(
                new AdminCommands()
        );
    }

    private static void registerListeners() {
        jda.addEventListener(
                new JDAPrefixCommandExecutor(commandManager),
                new JDASlashCommandExecutor(commandManager),
                new BotListener()
        );
    }
}
