package com.colzvr.bot.command.reader;

import com.wizardlybump17.wlib.command.args.reader.ArgsReader;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

@RequiredArgsConstructor
public class RoleArgsReader extends ArgsReader<Role> {

    private final Guild guild;

    @Override
    public Class<Role> getType() {
        return Role.class;
    }

    @Override
    public Role read(String s) {
        return guild.getRoleById(s);
    }
}
