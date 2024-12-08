package surc.simbay.snoweventremastered;

import surc.simbay.snoweventremastered.TabCompleter.ReloadCompleter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SnowJob extends JavaPlugin implements Listener, CommandExecutor {
   private static SnowJob instance;
   private Economy economy;

   public void onEnable() {
      instance = this;
      this.getCommand("snowevent").setExecutor(new SnowJobCommand());
      this.getCommand("snowevent").setTabCompleter(new ReloadCompleter());
      Config.loadConfig();
   }

   public void onDisable() {
      Config.saveConfig();
   }

   public static SnowJob getInstance() {
      return instance;
   }
}
