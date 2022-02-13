package com.colzvr.bot.impl.command;

import com.wizardlybump17.wlib.command.holder.Command;
import com.wizardlybump17.wlib.command.holder.CommandHolder;
import net.dv8tion.jda.api.JDA;

import java.util.HashMap;
import java.util.Map;

public class JDACommandHolder implements CommandHolder<JDA> {

    private final JDA jda;
    private final Map<String, Command> commands = new HashMap<>();

    public JDACommandHolder(JDA jda) {
        this.jda = jda;
    }

    @Override
    public Command getCommand(String s) {
        commands.putIfAbsent(s, new JDACommand());
        return commands.get(s);
    }

    @Override
    public JDA getHandle() {
        return jda;
    }
}
