package club.cpacket.solaros.manager.client;

import club.cpacket.solaros.api.command.Command;
import club.cpacket.solaros.impl.commands.PrefixCommand;
import club.cpacket.solaros.impl.commands.ConfigCommand;
import club.cpacket.solaros.impl.commands.ToggleCommand;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zzurio
 */

public class CommandManager {

    private static CommandManager commandManager;

    private final Set<Command> commands = new HashSet<>();

    private String prefix = "-";

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static Command get(final String commandStr) {
        Command command = null;

        for (Command commands : commandManager.getCommands()) {
            if (commands.getCommand().equalsIgnoreCase(commandStr)) {
                command = commands;

                break;
            }
        }

        return command;
    }

    public void init() {
        this.commands.add(new PrefixCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new ToggleCommand());
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Set<Command> getCommands() {
        return commands;
    }
}
