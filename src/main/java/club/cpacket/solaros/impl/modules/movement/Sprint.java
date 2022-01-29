package club.cpacket.solaros.impl.modules.movement;

import club.cpacket.solaros.api.event.events.entity.LivingUpdateEvent;
import club.cpacket.solaros.api.event.events.player.MotionEvent;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;
import event.bus.EventListener;

/**
 * @author zzurio
 */

@SuppressWarnings("unused")
public class Sprint extends Module {

    public Setting<Mode> mode = register(new Setting<>("Mode", "The mode for sprint.", Mode.DIRECTIONAL));
    public Setting<Boolean> strict = register(new Setting<>("Strict", "Changes sprint to function better in stricter anti-cheats.", false));

    public Sprint() {
        super("Sprint", "Keeps you always sprinting.", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        switch (mode.getValue()) {
            case DIRECTIONAL:
                mc.player.setSprinting(handleSprint() && isMoving());
                break;
            case NORMAL:
                mc.player.setSprinting(handleSprint() && isMoving() && !mc.player.collidedHorizontally && mc.gameSettings.keyBindForward.isKeyDown());
                break;
        }
    }

    @EventListener
    public void onMotion(MotionEvent event) {
        event.setCancelled(nullSafe() && handleSprint() && isMoving() && mode.getValue().equals(Mode.DIRECTIONAL));
    }

    @EventListener
    public void onLivingUpdate(LivingUpdateEvent event) {
        event.setCancelled(nullSafe() && handleSprint() && isMoving() && mode.getValue().equals(Mode.DIRECTIONAL));
    }

    /**
     * Convenience functions.
     */

    public static boolean isMoving() {
        return (mc.player.moveForward != 0 || mc.player.moveStrafing != 0);
    }

    public boolean handleSprint() {
        return (!mc.player.isHandActive() && !mc.player.isSneaking()) || !strict.getValue();
    }

    public enum Mode {
        DIRECTIONAL,
        NORMAL
    }
}