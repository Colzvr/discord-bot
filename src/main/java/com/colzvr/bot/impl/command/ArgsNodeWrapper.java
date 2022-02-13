package com.colzvr.bot.impl.command;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ArgsNodeWrapper {

    private static final Map<Class<?>, OptionType> TYPES = new HashMap<>();

    static {
        TYPES.put(byte.class, OptionType.INTEGER);
        TYPES.put(short.class, OptionType.INTEGER);
        TYPES.put(int.class, OptionType.INTEGER);
        TYPES.put(long.class, OptionType.STRING);
        TYPES.put(float.class, OptionType.STRING);
        TYPES.put(double.class, OptionType.STRING);
        TYPES.put(char.class, OptionType.STRING);
        TYPES.put(String.class, OptionType.STRING);
        TYPES.put(boolean.class, OptionType.BOOLEAN);
        TYPES.put(Member.class, OptionType.USER);
        TYPES.put(TextChannel.class, OptionType.CHANNEL);
        TYPES.put(VoiceChannel.class, OptionType.CHANNEL);
        TYPES.put(Role.class, OptionType.ROLE);
    }

    public static OptionType wrap(Class<?> clazz) {
        return TYPES.getOrDefault(clazz, OptionType.STRING);
    }
}
