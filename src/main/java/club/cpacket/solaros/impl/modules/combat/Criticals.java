package club.cpacket.solaros.impl.modules.combat;

import club.cpacket.solaros.api.event.events.network.PacketEvent;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.setting.Setting;
import com.mojang.realmsclient.gui.ChatFormatting;
import event.bus.EventListener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Criticals extends Module {

    public Setting<Mode> mode = register(new Setting<>("Mode", "Mode used to make critic damage.", Mode.PACKET));

    public Criticals() {
        super("Criticals", "Allows you to always make critical damage.", Category.COMBAT);
    }

    @EventListener
    public void onPacketSend(PacketEvent.Send event) {
        if (!nullSafe()) return;
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase) {
                    switch (mode.getValue()) {
                        case JUMP: {
                            mc.player.jump();
                            break;
                        }
                        case PACKET: {
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.11, mc.player.posZ, false));
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                            break;
                        }
                    }
                }
            }
        }
    }

    public String getDisplayInfo() {
        return ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + mode.getValue().toString() + ChatFormatting.GRAY + "]";
    }

    enum Mode {
        PACKET,
        JUMP
    }
}