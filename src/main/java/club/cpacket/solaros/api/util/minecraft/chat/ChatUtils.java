package club.cpacket.solaros.api.util.minecraft.chat;

import club.cpacket.solaros.api.util.minecraft.IGlobals;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

/**
 * @author zzurio
 */

public class ChatUtils implements IGlobals {

    public static final String SOLAROS = ChatFormatting.AQUA + "[" + ChatFormatting.BLUE + "Solaros" + ChatFormatting.AQUA + "]" + ChatFormatting.RESET;

    public static final String SPACE = " ";

    public static void sendMessage(String message) {
        if (mc.ingameGUI == null || mc.ingameGUI.getChatGUI() == null) {
            return;
        }
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(SOLAROS + SPACE + message));
    }

    public static void sendOverwriteMessage(String message) {
        if (mc.ingameGUI == null) return;
        String commandMessage = SOLAROS + SPACE + message;
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(commandMessage), 69); // 69 woo.
    }
}
