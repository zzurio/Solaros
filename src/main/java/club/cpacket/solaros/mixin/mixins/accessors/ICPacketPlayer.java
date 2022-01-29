package club.cpacket.solaros.mixin.mixins.accessors;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author zzurio
 */

@Mixin(CPacketPlayer.class)
public interface ICPacketPlayer {

    @Accessor("rotating")
    boolean isRotating();

    @Accessor("moving")
    boolean isMoving();

    @Accessor("yaw")
    void setYaw(float yaw);

    @Accessor("pitch")
    void setPitch(float pitch);

    @Accessor("onGround")
    void setOnGround(boolean onGround);
}