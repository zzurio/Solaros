package club.cpacket.solaros.impl.modules.misc;

import club.cpacket.solaros.api.event.events.network.PacketEvent;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;
import club.cpacket.solaros.api.setting.settings.ColorPicker;
import club.cpacket.solaros.api.util.gl.RenderUtils;
import event.bus.EventListener;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author zzurio
 */

public class ChorusControl extends Module {

    public Setting<Boolean> cpacketplayer = register(new Setting<>("CPacketPlayer", "Cancels the CPacketPlayer packet.", true));
    public Setting<Boolean> spacketplayerposlook = register(new Setting<>("SPacketPlayerPosLook", "Cancels the SPacketPlayerPosLook packet.", true));

    public Setting<Boolean> render = register(new Setting<>("Render", "Renders the predicted teleport spot.", true));
    public Setting<Double> slabHeight = register(new Setting<>("Height", "The height of the slab.", 0.1, -1.0, 1.0)).withParent(render);
    public Setting<ColorPicker> color = register(new Setting<>("Color", "The color of the slab.", new ColorPicker(Color.BLUE)));

    Queue<CPacketPlayer> packets = new LinkedList<>();
    Queue<CPacketConfirmTeleport> teleportPackets = new LinkedList<>();

    SPacketPlayerPosLook sPacketPlayerPosLook;

    //private boolean keyDown;

    public ChorusControl() {
        super("ChorusControl", "Lets you control your chorus teleportation.", Category.MISC);
    }

    @EventListener
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            sPacketPlayerPosLook = (SPacketPlayerPosLook) event.getPacket();
            if (spacketplayerposlook.getValue()) event.cancel();
        }

        if (event.getPacket() instanceof CPacketPlayer) {
            packets.add(((CPacketPlayer) event.getPacket()));

            if (cpacketplayer.getValue())
                event.cancel();
        }

        if (event.getPacket() instanceof CPacketConfirmTeleport) {
            teleportPackets.add(((CPacketConfirmTeleport) event.getPacket()));
            event.cancel();
        }
    }

    @Override
    public void onDisable() {
        while (!this.packets.isEmpty()) {
            mc.getConnection().sendPacket(Objects.requireNonNull(this.packets.poll()));
        }
        while (!this.teleportPackets.isEmpty()) {
            mc.getConnection().sendPacket(Objects.requireNonNull(this.teleportPackets.poll()));
        }
        sPacketPlayerPosLook = null;
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (sPacketPlayerPosLook == null) return;
        if (!render.getValue()) return;

        BlockPos pos = new BlockPos(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());

        RenderUtils.drawBBSlab(new AxisAlignedBB(pos), slabHeight.getValue(), color.getValue().getColor());
    }

    @Override
    public void onLogout() {
        // Disable module, so you can join a server once you open up minecraft. LMAO
        this.setEnabled(false);
    }
}
