package surc.simbay.snoweventremastered;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
   private static Plugin plugin;
   private static FileConfiguration config;

   public static void setPlugin(Plugin plugin) {
      Config.plugin = plugin;
   }

   public static Plugin getPlugin() {
      return plugin;
   }

   public static Server getServer() {
      return plugin.getServer();
   }

   public static void loadConfig() {
      SnowEventRemastered.getInstance().saveDefaultConfig();
      config = SnowEventRemastered.getInstance().getConfig();
      plugin.saveDefaultConfig();
      config = plugin.getConfig();
   }

   public static void saveConfig() {
      SnowEventRemastered.getInstance().saveConfig();
   }

   public static FileConfiguration getConfig() {
      return config;
   }
}
