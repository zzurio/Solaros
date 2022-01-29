package club.cpacket.solaros.api.command;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.api.util.minecraft.chat.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

/**
 * @author zzurio
 */

public class Command {

    private final String command;
    private final String description;

    public Command(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String theCommand() {
        return "";
    }

    /*public List<String> listArguments(String[] args, int indexLimit) {
        final List<String> list = new ArrayList<>();

        if (args.length > 0 && indexLimit != 0) {
            return list;
        }

        list.addAll(Arrays.asList(args).subList(1, args.length));

        if (list.size() != indexLimit) {
            this.splash(CommandState.ERROR);

            return null;
        }

        return list;
    }*/

    public void splash(CommandState state) {
        switch (state) {
            case ERROR: {
                ChatUtils.sendMessage(ChatFormatting.RED + "Invalid syntax. Try: '" + Solaros.INSTANCE.commandManager.getPrefix() + theCommand() + "'");

                break;
            }

            case PERFORMED: {
                break;
            }
        }
    }

    public void onCommand(String[] args) {
    }
}
