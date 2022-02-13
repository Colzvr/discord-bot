package com.colzvr.bot.listener;

import com.colzvr.bot.Main;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        if (!event.getGuild().getId().equals(Main.COLZVR))
            event.getGuild().leave().queue();
    }
}
