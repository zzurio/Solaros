package club.cpacket.solaros;

import club.cpacket.solaros.gui.panelstudio.PanelStudioGUI;
import club.cpacket.solaros.manager.client.CommandManager;
import club.cpacket.solaros.manager.client.ConfigManager;
import club.cpacket.solaros.manager.client.ModuleManager;
import club.cpacket.solaros.manager.client.RotationManager;
import club.cpacket.solaros.manager.forge.ForgeEventManager;
import event.bus.EventBus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = "solaros",
        version = "0.0.1"
)

/**
 * @author zzurio
 */

public class Solaros {

    public static final String PATH = "Solaros/";
    public static final String CONFIG_PATH = PATH + "configs/";

    public static EventBus EVENT_BUS = EventBus.INSTANCE;
    public static PanelStudioGUI gui;

    public ForgeEventManager forgeEventManager;
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public RotationManager rotationManager;

    public static void startup() {
        ConfigManager.refresh();
        ConfigManager.reload();
        ConfigManager.process(ConfigManager.LOAD);
    }

    public static void shutdown() {
        ConfigManager.reload();
        ConfigManager.process(ConfigManager.SAVE);
    }

    @Mod.Instance
    public static Solaros INSTANCE;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.whoosh("Solaros");
    }

    public void whoosh(final String whoosh) {
        this.moduleManager = new ModuleManager();
        this.moduleManager.init();

        this.commandManager = new CommandManager();
        this.commandManager.init();

        this.forgeEventManager = new ForgeEventManager();
        this.forgeEventManager.init();

        this.configManager = new ConfigManager();
        this.configManager.init();

        this.rotationManager = new RotationManager();
        this.rotationManager.init();

        gui = new PanelStudioGUI();

        MinecraftForge.EVENT_BUS.register(this.forgeEventManager);

        startup();

        Runtime.getRuntime().addShutdownHook(new Thread("Solaros ShutdownHook") {
            @Override
            public void run() {
                Solaros.shutdown();
            }
        });
    }
}