package com.colzvr.bot.command;

import com.colzvr.bot.impl.command.MemberContextSender;
import com.wizardlybump17.wlib.command.Command;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class AdminCommands {

    private static final String ANNOUNCEMENTS_CHANNEL = "807395625397518407";

    @Command(execution = "announce <message>", permission = "MANAGE_SERVER", options = {"slash"})
    public void announce(MemberContextSender sender, String[] message) {
        sender.deleteMessage();

        GuildChannel channel = sender.guild().getGuildChannelById(ANNOUNCEMENTS_CHANNEL);
        if (!(channel instanceof TextChannel textChannel))
            return;

        textChannel.sendMessage(String.join(" ", message).replace("\\n", "\n")).queue();
    }

    @Command(execution = "clear <amount>", permission = "MESSAGE_MANAGE", options = {"slash"})
    public void clear(MemberContextSender sender, int amount) {
        if (amount > 99) {
            sender.slashEvent().reply("You can only delete 99 messages at a time.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        sender.channel().getHistory().retrievePast(amount + 1).queue(messages -> {
            for (Message message : messages)
                message.delete().queue();
        });
    }
}
