package club.cpacket.solaros.impl.modules.render;

import club.cpacket.solaros.api.event.events.render.AspectEvent;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;
import event.bus.EventListener;

/**
 * @author zzurio
 */

public class Aspect extends Module {

    public Setting<Float> aspect = register(new Setting("Aspect", "The aspect.", mc.displayWidth / mc.displayHeight + 0.0f, 0.1f, 3.0f));

    public Aspect() {
        super("Aspect", "Lets you modify the aspect ratio.", Category.RENDER);
    }

    @EventListener
    public void onAspect(AspectEvent event) {
        if (!nullSafe()) return;
        event.setAspect(aspect.getValue());
    }
}
