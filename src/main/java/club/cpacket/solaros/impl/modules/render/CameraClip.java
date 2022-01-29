package club.cpacket.solaros.impl.modules.render;

import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;

/**
 * @author zzurio
 * {@link club.cpacket.solaros.mixin.mixins.entity.MixinEntityRenderer}
 */

public class CameraClip extends Module {

    public Setting<Boolean> extend = register(new Setting<>("Extend", "Choose whether to extend the camera clipping.", false));
    public Setting<Double> distance = register(new Setting<>("Distance", "The distance to clip the camera.", 10.0, 0.0, 50.0));

    public CameraClip() {
        super("CameraClip", "Lets the camera clip through blocks in third person.", Category.RENDER);
    }
}
