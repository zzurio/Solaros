package club.cpacket.solaros.api.event.events.player;

import club.cpacket.solaros.api.event.Event;
import net.minecraft.entity.MoverType;

/**
 * @author zzurio
 */

public class MotionEvent extends Event {

    private MoverType type;
    private double x;
    private double y;
    private double z;

    public MotionEvent(MoverType type, double x, double y, double z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MoverType getType() {
        return type;
    }

    public void setType(MoverType type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}