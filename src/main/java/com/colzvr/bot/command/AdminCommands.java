package com.colzvr.bot.command;

import com.colzvr.bot.impl.command.MemberContextSender;
import com.wizardlybump17.wlib.command.Command;
import com.wizardlybump17.wlib.command.Description;
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
    public void clear(MemberContextSender sender, @Description("How many messages it should delete") int amount) {
        if (amount > 100) {
            sender.slashEvent().reply("You can only delete 100 messages at a time.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        sender.channel().getHistory().retrievePast(amount).queue(messages -> {
            for (Message message : messages)
                message.delete().queue();
        });

        sender.slashEvent().reply("Deleting...").setEphemeral(true).queue();
    }

//    @Command(execution = "spam <amount>", permission = "MANAGE_SERVER", options = {"slash"})
//    public void spam(MemberContextSender sender, @Description("How many messages it should spam") int amount) {
//        for (int i = 0; i < amount; i++)
//            sender.sendMessage("SPAM");
//
//        sender.slashEvent().reply("Spamming...").setEphemeral(true).queue();
//    }

    @Command(execution = "spam <amount> <message>", permission = "MANAGE_SERVER", options = {"slash"})
    public void spam(MemberContextSender sender, @Description("How many messages it should spam") int amount, @Description("The message to spam") String[] message) {
        for (int i = 0; i < amount; i++)
            sender.sendMessage(String.join(" ", message));

        sender.slashEvent().reply("Spamming...").setEphemeral(true).queue();
    }
}
