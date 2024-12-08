package surc.simbay.snoweventremastered;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SnowJobCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof Player) {
         Player player = (Player)sender;
         if (label.equalsIgnoreCase("snowevent") && args.length > 0 && args[0].equalsIgnoreCase("toggle")) {
            this.toggleSnowJobStatus(player);
            return true;
         }

         if (label.equalsIgnoreCase("snowevent") && args.length > 0 && args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&1&8&f&b&d&9&lSnowEvent &7» &fИспользуйте &b/snowevent &ejoin&7|&eleave&7|&ereload"));
            return true;
         }

         if (label.equalsIgnoreCase("snowevent") && args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&1&8&f&b&d&9&lSnowEvent &7» &aКонфигурация перезагружена"));
            SnowEventRemastered.getInstance().reloadConfig();
            return true;
         }

         if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&1&8&f&b&d&9&lSnowEvent &7» &fИспользуйте /snowevent &ejoin&7|&eleave&7|&ereload"));
            return true;
         }

         if (args[0].equalsIgnoreCase("join")) {
            if (!SnowJobManager.isPlayerHired(player)) {
               SnowJobManager.hirePlayer(player);
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.join")));
            } else {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.working")));
            }
         } else if (args[0].equalsIgnoreCase("leave")) {
            if (SnowJobManager.isPlayerHired(player)) {
               SnowJobManager.firePlayer(player);
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.leave")));
            } else {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.leaved")));
            }
         } else {
            player.sendMessage("Неверная команда. Используйте /se join или /se leave");
         }
      } else {
         sender.sendMessage("Команду можно использовать только в игре.");
      }

      return true;
   }

   private void toggleSnowJobStatus(Player player) {
      if (SnowJobManager.isPlayerHired(player)) {
         SnowJobManager.firePlayer(player);
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.leave")));
      } else {
         SnowJobManager.hirePlayer(player);
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.join")));
      }

   }
}
