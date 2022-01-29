package club.cpacket.solaros.impl.modules.render;

import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;

/**
 * @author zzurio
 */

public class NoInterpolation extends Module {

    public NoInterpolation() {
        super("NoInterpolation", "Renders server-side player positions.", Category.RENDER);
    }
}
