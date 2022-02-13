package com.colzvr.bot.impl.command;

import com.wizardlybump17.wlib.command.CommandSender;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public record MemberContextSender(
        Member member,
        Message message,
        TextChannel channel,
        Guild guild,
        SlashCommandInteractionEvent slashEvent
) implements CommandSender<Member> {

    @Override
    public Member getHandle() {
        return member;
    }

    @Override
    public void sendMessage(String s) {
        channel.sendMessage(s).queue(message -> {}, e -> {
            if (e != null)
                throw new IllegalStateException(e);
        });
    }

    @Override
    public void sendMessage(String... strings) {
        for (String string : strings)
            sendMessage(string);
    }

    public void sendPrivateMessage(String... message) {
        member.getUser().openPrivateChannel().queue(
                channel -> {
                    for (String s : message)
                        channel.sendMessage(s).queue();
                },
                throwable -> {
                    if (throwable != null)
                        throw new IllegalStateException(throwable);
                }
        );
    }

    public void deleteMessage() {
        message.delete().queue();
    }

    @Override
    public String getName() {
        return member.getEffectiveName();
    }

    @Override
    public boolean hasPermission(String s) {
        return member.hasPermission(Permission.valueOf(s.toUpperCase()));
    }
}
