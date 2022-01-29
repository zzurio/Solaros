package club.cpacket.solaros.api.util.minecraft.rotation;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.api.util.minecraft.IGlobals;

/**
 * @author zzurio
 */

public class Rotation implements IGlobals {

    private final Rotate rotate;
    private float yaw;
    private float pitch;

    public Rotation(float yaw, float pitch, Rotate rotate) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.rotate = rotate;
    }

    public void updateModelRotations() {
        if (mc.player != null && mc.world != null) {
            switch (rotate) {
                case PACKET:
                    mc.player.renderYawOffset = this.yaw;
                    mc.player.rotationYawHead = this.yaw;
                    Solaros.INSTANCE.rotationManager.setHeadPitch(this.pitch);
                    break;
                case CLIENT:
                    mc.player.rotationYaw = this.yaw;
                    mc.player.rotationPitch = this.pitch;
                    break;
                case NONE:
                    break;
            }
        }
    }

    public void restoreRotations() {
        if (mc.world != null && mc.player != null) {
            this.yaw = mc.player.rotationYaw;
            this.pitch = mc.player.rotationPitch;
        }
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Rotate getRotation() {
        return this.rotate;
    }

    public enum Rotate {
        PACKET, CLIENT, NONE
    }
}
