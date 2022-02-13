package com.colzvr.bot.impl.command;

import com.wizardlybump17.wlib.command.holder.Command;
import com.wizardlybump17.wlib.command.holder.CommandExecutor;

public class JDACommand implements Command {

    private CommandExecutor executor;

    @Override
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public CommandExecutor getExecutor() {
        return executor;
    }
}
