package club.cpacket.solaros.impl.modules.client;

import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;

/**
 * @author zzurio
 */

public class Notifications extends Module {

    public Setting<Boolean> modules = register(new Setting<>("Modules", "Chat notifications when a module is enabled or disabled.", true));

    public Notifications() {
        super("Notifications", "Handle various notifications.", Category.CLIENT);
    }
}
