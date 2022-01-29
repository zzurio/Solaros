package club.cpacket.solaros.impl.commands;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.api.command.Command;
import club.cpacket.solaros.api.command.CommandState;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.util.minecraft.chat.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

/**
 * @author zzurio
 */

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", "Enable or disable a module.");
    }

    @Override
    public String theCommand() {
        return "toggle <module>";
    }

    @Override
    public void onCommand(String[] args) {
        String module = null;

        if (args.length > 1) {
            module = args[1];
        }

        if (module == null || args.length > 2) {
            this.splash(CommandState.ERROR);
            return;
        }

        boolean isModule = false;

        for (Module modules : Solaros.INSTANCE.moduleManager.getModules()) {
            if (modules.getName().equalsIgnoreCase(module)) {
                Solaros.INSTANCE.moduleManager.getModuleByString(module).toggle();
                isModule = true;
                break;
            }
        }
        if (!isModule) {
            ChatUtils.sendMessage(ChatFormatting.RED + "Unknown module.");
        } else {
            ChatUtils.sendMessage(ChatFormatting.BOLD + module + ChatFormatting.RESET + " " + "has been toggled.");
        }

        this.splash(CommandState.PERFORMED);
    }
}