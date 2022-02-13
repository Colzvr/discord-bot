package com.colzvr.bot.listener;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotListener extends ListenerAdapter {

    public static final String COLZVR = "807386597690966038";

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        if (!event.getGuild().getId().equals(COLZVR))
            event.getGuild().leave().queue();
    }
}
