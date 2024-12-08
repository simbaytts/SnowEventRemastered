package surc.simbay.snoweventremastered;

import surc.simbay.snoweventremastered.TabCompleter.ReloadCompleter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SnowEventRemastered extends JavaPlugin {
    private static SnowEventRemastered instance;
    private Location firstPoint;
    private Location secondPoint;

    public void onEnable() {
        instance = this;
        Config.setPlugin(this);
        this.getCommand("snoweventremastered").setExecutor(new SnowJobCommand());
        this.getCommand("snoweventremastered").setTabCompleter(new ReloadCompleter());
        BlockBreakListener blockBreakListener = new BlockBreakListener();
        this.setupEconomy(blockBreakListener);
        this.getServer().getPluginManager().registerEvents(blockBreakListener, this);
        Config.loadConfig();
    }

    private void setupEconomy(BlockBreakListener blockBreakListener) {
        if (this.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp != null) {
                Economy economy = (Economy)rsp.getProvider();
            }
        }
    }

    public void onDisable() {
        Config.saveConfig();
    }

    public static SnowEventRemastered getInstance() {
        return instance;
    }
}
