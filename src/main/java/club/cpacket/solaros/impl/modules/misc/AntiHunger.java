package club.cpacket.solaros.impl.modules.misc;

import club.cpacket.solaros.api.event.events.network.PacketEvent;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import event.bus.EventListener;
import net.minecraft.network.play.client.CPacketEntityAction;

/**
 * @author Paupro
 */

public class AntiHunger extends Module {

    public AntiHunger() {
        super("AntiHunger", "Reduces the hunger effect.", Category.MISC);
    }

    @EventListener
    public void onPacketSend(PacketEvent.Send event) {
        if (!nullSafe()) return;
        if (event.getPacket() instanceof CPacketEntityAction) {
            CPacketEntityAction packet = (CPacketEntityAction) event.getPacket();
            if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING || packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                event.setCancelled(true);
            }
        }
    }
}
