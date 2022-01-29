package club.cpacket.solaros.manager.client;

import club.cpacket.solaros.api.event.events.network.PacketEvent;
import club.cpacket.solaros.api.event.events.render.RenderLivingEntityEvent;
import club.cpacket.solaros.api.util.minecraft.IGlobals;
import club.cpacket.solaros.api.util.minecraft.rotation.Rotation;
import event.bus.EventListener;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author zzurio
 */

@SuppressWarnings("unused")
public class RotationManager implements IGlobals {

    private float headPitch = -1;
    private Rotation serverRotation = new Rotation(0, 0, Rotation.Rotate.NONE);

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player != null && mc.world != null) {
            headPitch = -1;
        }
    }

    @EventListener
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer)
            serverRotation = new Rotation(((CPacketPlayer) event.getPacket()).getYaw(0), ((CPacketPlayer) event.getPacket()).getPitch(0), Rotation.Rotate.NONE);
    }

    @SubscribeEvent
    public void onRenderLivingEntity(RenderLivingEntityEvent event) {
        if (event.getEntityLivingBase().equals(mc.player)) {
            event.setCanceled(true);
            event.getModelBase().render(event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), headPitch == -1 ? mc.player.rotationPitch : headPitch, event.getScaleFactor());
        }
    }

    public void setHeadPitch(float in) {
        headPitch = in;
    }

    public Rotation getServerRotation() {
        return this.serverRotation;
    }
}