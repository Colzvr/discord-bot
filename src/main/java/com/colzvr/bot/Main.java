package com.colzvr.bot;

import com.colzvr.bot.command.AdminCommands;
import com.colzvr.bot.impl.command.JDACommandHolder;
import com.colzvr.bot.impl.command.executor.JDAPrefixCommandExecutor;
import com.colzvr.bot.impl.command.executor.JDASlashCommandExecutor;
import com.colzvr.bot.listener.BotListener;
import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.command.RegisteredCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import javax.security.auth.login.LoginException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static final String COLZVR = "807386597690966038";

    private static JDA jda;
    private static CommandManager commandManager;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault(System.getenv("token")).build();

        for (Command command : jda.retrieveCommands().complete()) {
            jda.deleteCommandById(command.getId()).complete();
        }

        initCommandManager();
        leaveGuilds();
        registerListeners();

        jda.getGatewayPing();
    }

    private static void leaveGuilds() {
        for (Guild guild : jda.getGuilds())
            if (!guild.getId().equals(COLZVR))
                guild.leave().queue();
    }

    private static void initCommandManager() {
        commandManager = new CommandManager(new JDACommandHolder(jda));

        commandManager.registerCommands(
                new AdminCommands()
        );

        List<RegisteredCommand> commands = commandManager.getCommands();

        Set<CommandData> commandsData = new HashSet<>();

        for (RegisteredCommand command : commands) {
            for (String option : command.getCommand().options()) {
                if (!option.equalsIgnoreCase("slash"))
                    continue;

                String description = command.getCommand().description();
                if (description.isEmpty())
                    description = command.getCommand().execution();

                commandsData.add(new CommandDataImpl(
                        command.getName(),
                        description
                ));
            }
        }

        jda.updateCommands().addCommands(commandsData).complete();
    }

    private static void registerListeners() {
        jda.addEventListener(
                new JDAPrefixCommandExecutor(commandManager),
                new JDASlashCommandExecutor(commandManager),
                new BotListener()
        );
    }
}
