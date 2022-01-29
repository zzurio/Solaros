package club.cpacket.solaros.impl.modules.misc;

import club.cpacket.solaros.api.event.events.network.PacketEvent;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import event.bus.EventListener;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

/**
 * @author zzurio
 */

public class NoHandshake extends Module {

    public NoHandshake() {
        super("NoHandshake", "Prevents sending your modlist to the server.", Category.MISC);
    }

    @EventListener
    public void onPacketReceive(PacketEvent.Receive event) {
        if (!nullSafe()) return;
        if (mc.isSingleplayer()) return;
        CPacketCustomPayload packet;
        if (event.getPacket() instanceof FMLProxyPacket && !mc.isSingleplayer()) {
            event.cancel();
        }
        if (event.getPacket() instanceof CPacketCustomPayload && (packet = (CPacketCustomPayload) event.getPacket()).getChannelName().equals("MC|Brand")) {
            packet.data = new PacketBuffer(Unpooled.buffer()).writeString("vanilla");
        }

    }
}
