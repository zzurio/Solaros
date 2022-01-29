package club.cpacket.solaros.impl.modules.movement;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author zzurio
 */

public class AutoWalk extends Module {

    public Setting<Boolean> sprint = register(new Setting<>("Sprint", "Enables sprinting when you auto walk.", false));

    public AutoWalk() {
        super("AutoWalk", "Automatically walks forward for you.", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdateInput(InputUpdateEvent event) {
        if (!nullSafe()) return;
        if (sprint.getValue()) {
            Solaros.INSTANCE.moduleManager.getModuleByClass(Sprint.class).setEnabled(true);
        }
        event.getMovementInput().moveForward = 1;
    }

    @Override
    public void onDisable() {
        if (sprint.getValue()) {
            Solaros.INSTANCE.moduleManager.getModuleByClass(Sprint.class).setEnabled(false);
        }
    }
}
