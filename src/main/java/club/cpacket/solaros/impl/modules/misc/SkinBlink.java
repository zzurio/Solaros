package club.cpacket.solaros.impl.modules.misc;

import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import net.minecraft.entity.player.EnumPlayerModelParts;

/**
 * @author zzurio
 */

public class SkinBlink extends Module {

    static EnumPlayerModelParts[] PARTS_HORIZONTAL;

    static {
        PARTS_HORIZONTAL = new EnumPlayerModelParts[]{EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE};
    }

    public SkinBlink() {
        super("SkinBlink", "Turns skin layers on and off.", Category.MISC);
    }

    @Override
    public void onUpdate() {
        if (!nullSafe()) return;
        int delay = mc.player.ticksExisted % (PARTS_HORIZONTAL.length * 2);
        boolean renderLayer = false;

        if (delay >= PARTS_HORIZONTAL.length) {
            renderLayer = true;
            delay -= PARTS_HORIZONTAL.length;
        }

        mc.gameSettings.setModelPartEnabled(PARTS_HORIZONTAL[delay], renderLayer);
    }
}
