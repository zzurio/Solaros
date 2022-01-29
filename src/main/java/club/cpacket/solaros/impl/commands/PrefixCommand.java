package club.cpacket.solaros.impl.commands;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.api.command.Command;
import club.cpacket.solaros.api.command.CommandState;
import club.cpacket.solaros.api.util.minecraft.chat.ChatUtils;
import club.cpacket.solaros.manager.client.CommandManager;
import com.mojang.realmsclient.gui.ChatFormatting;

/**
 * @author zzurio
 */

public class PrefixCommand extends Command {

    public PrefixCommand() {
        super("prefix", "Change the command prefix.");
    }

    @Override
    public String theCommand() {
        return "prefix <char>";
    }

    @Override
    public void onCommand(String[] args) {
        String character = null;

        if (args.length > 1) {
            character = args[1];
        }

        if (character == null || args.length > 2) {
            this.splash(CommandState.ERROR);

            return;
        }

        Solaros.INSTANCE.commandManager.setPrefix(character);

        ChatUtils.sendMessage("Prefix changed to " + ChatFormatting.WHITE + character);

        this.splash(CommandState.PERFORMED);
    }
}