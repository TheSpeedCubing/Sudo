package speedcubing.bungeesudo;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new sudo());
    }
}
