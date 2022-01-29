package club.cpacket.solaros.api.event.events.entity;

import club.cpacket.solaros.api.event.Event;
import net.minecraft.client.entity.EntityPlayerSP;

/**
 * @author zzurio
 */

public class LivingUpdateEvent extends Event {

    private final EntityPlayerSP entityPlayerSP;
    private final boolean sprinting;

    public LivingUpdateEvent(EntityPlayerSP entityPlayerSP, boolean sprinting) {
        this.entityPlayerSP = entityPlayerSP;
        this.sprinting = sprinting;
    }

    public EntityPlayerSP getEntityPlayerSP() {
        return this.entityPlayerSP;
    }

    public boolean isSprinting() {
        return this.sprinting;
    }
}