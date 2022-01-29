package club.cpacket.solaros.impl.modules.client;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;
import org.lwjgl.input.Keyboard;

/**
 * @author zzurio
 */

public class ClickGUI extends Module {

    public static ClickGUI clickGUI;

    public Setting<Integer> rainbowGradient = register(new Setting<>("Rainbow Gradient", "How fast the rainbow should repeat.", 150, 50, 300));

    public Setting<Integer> scrollSpeed = register(new Setting<>("Scroll Speed", "The speed of scrolling.", 10, 0, 20));

    public Setting<Integer> animationSpeed = register(new Setting<>("Animation Speed", "The speed of GUI animations.", 200, 0, 1000));

    public Setting<Layout> layout = register(new Setting<>("Layout", "The layout of the GUI.", Layout.CSGOCategory));

    public ClickGUI() {
        super("ClickGUI", "Clickable GUI inspired by Skeet.CC.", Category.CLIENT);

        this.getBind().setKey(Keyboard.KEY_RSHIFT);

        clickGUI = this;
    }

    @Override
    public void onEnable() {
        if (mc.currentScreen == null) {
            Solaros.gui.enterGUI();
        }
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen == Solaros.gui) {
            mc.displayGuiScreen(null);
        }
    }

    public enum Layout {
        ClassicPanel, PopupPanel, DraggablePanel, SinglePanel, PanelMenu, ColorPanel, CSGOHorizontal, CSGOVertical, CSGOCategory, SearchableCSGO
    }
}
