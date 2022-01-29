package club.cpacket.solaros.impl.modules.misc;

import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import net.minecraft.init.MobEffects;

/**
 * @author Paupro
 */

public class AntiLevitation extends Module {

    public AntiLevitation() {
        super("AntiLevitation", "Removes the levitation effect.", Category.MISC);
    }

    @Override
    public void onUpdate() {
        if (!nullSafe()) return;
        mc.player.removePotionEffect(MobEffects.LEVITATION);
    }
}
