package speedcubing.bungeesudo;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.util.*;

public class sudo extends Command implements TabExecutor {
    public sudo() {
        super("sudo");
    }

    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer ? commandSender.hasPermission("sudo.use") : commandSender instanceof ConsoleCommandSender) {
            if (strings.length < 2) {
                commandSender.sendMessage(new TextComponent("/sudo <player> <command>"));
            } else {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(strings[0]);
                if (target != null) {
                    String str = "";
                    int length = strings.length;
                    for (int i = 1; i < length; i++) {
                        str += " " + strings[i];
                    }

                    String command = strings[1];
                    Set<String> CommandAndAlias = new HashSet<>();
                    for (Map.Entry e : ProxyServer.getInstance().getPluginManager().getCommands()) {
                        CommandAndAlias.add(e.getKey().toString());
                    }
                    if (command.startsWith("/") && CommandAndAlias.contains(command.substring(1)))
                        BungeeCord.getInstance().getPluginManager().dispatchCommand(target, str.substring(2));
                    else
                        target.chat(str.substring(1));
                } else {
                    commandSender.sendMessage(new TextComponent("Offline or not exist."));
                }
            }
        } else commandSender.sendMessage(new TextComponent("You don't have permission to perform this command !"));
    }


    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        List<String> names = new ArrayList<>();
        if (strings.length == 1) {
            String str = strings[0].toLowerCase();
            for (ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
                String name = p.getName();
                if (name.toLowerCase().startsWith(str))
                    names.add(name);
            }
        }
        return names;
    }
}
