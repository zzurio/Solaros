package club.cpacket.solaros.impl.modules.movement;

import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;
import club.cpacket.solaros.mixin.mixins.accessors.IEntity;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.CPacketEntityAction;

import static net.minecraft.network.play.client.CPacketEntityAction.Action.START_SNEAKING;
import static net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SNEAKING;

/**
 * @author zzurio
 */

public class WebBypass extends Module {

    public Setting<Mode> mode = register(new Setting<>("Mode", "The mode of web bypass.", Mode.VANILLA));
    public Setting<Boolean> sneak = register(new Setting<>("Sneak", "Sneaks whilst falling through the web.", false));


    public WebBypass() {
        super("WebBypass", "Prevents you from slowing down in webs.", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (!nullSafe()) {
            return;
        }
        boolean sneaking = mc.player.isSneaking();
        if (((IEntity) mc.player).isInWeb()) {
            switch (mode.getValue()) {
                case STRICT:
                    for (int i = 0; i < 10; i++)
                        if (sneak.getValue()) {
                            if (sneaking) {
                                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, START_SNEAKING));
                            }
                        }
                    mc.player.motionY--;
                    if (sneak.getValue()) {
                        if (sneaking) {
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, STOP_SNEAKING));
                        }
                    }
                    break;
                case VANILLA:
                    mc.player.isInWeb = false;
                    break;
            }
        }
    }

    @Override
    public String getDisplayInfo() {
        return ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + mode.getValue().toString() + ChatFormatting.GRAY + "]";
    }

    public enum Mode {
        STRICT,
        VANILLA
    }
}
