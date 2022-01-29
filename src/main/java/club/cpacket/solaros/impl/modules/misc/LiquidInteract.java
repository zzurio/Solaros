package club.cpacket.solaros.impl.modules.misc;

import club.cpacket.solaros.api.event.events.blocks.CanCollideCheckEvent;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import event.bus.EventListener;

/**
 * @author zzurio
 */

public class LiquidInteract extends Module {

    public LiquidInteract() {
        super("LiquidInteract", "Allows you to place blocks in liquids.", Category.MISC);
    }

    @EventListener
    public void canCollide(CanCollideCheckEvent event) {
        if (!nullSafe()) return;
        event.cancel();
    }
}
