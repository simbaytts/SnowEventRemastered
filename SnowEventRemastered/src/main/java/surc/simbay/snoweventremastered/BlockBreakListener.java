package surc.simbay.snoweventremastered;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import java.util.Random;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class BlockBreakListener implements Listener {
   private Economy economy;

   public BlockBreakListener() {
      this.setupEconomy();
   }

   private void setupEconomy() {
      if (Config.getPlugin().getServer().getPluginManager().getPlugin("Vault") != null) {
         RegisteredServiceProvider<Economy> rsp = Config.getPlugin().getServer().getServicesManager().getRegistration(Economy.class);
         if (rsp != null) {
            this.economy = (Economy)rsp.getProvider();
         }
      }

   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      Player player = event.getPlayer();
      Block block = event.getBlock();
      if (block.getType() == Material.SNOW_BLOCK && this.isPlayerInSnowEventRegion(player) && SnowJobManager.isPlayerHired(player) && this.economy != null) {
         String soundName = Config.getConfig().getString("sounds.sound");
         Sound Sound = null;
         if (soundName != null && !soundName.isEmpty()) {
            try {
               Sound = Sound.valueOf(soundName);
            } catch (IllegalArgumentException var10) {
               var10.printStackTrace();
            }
         }

         int rewardAmount = Config.getConfig().getInt("currency.reward_amount");
         String messageEnabled = Config.getConfig().getString("enable.message");
         String actionbarEnabled = Config.getConfig().getString("enable.actionbar");
         this.economy.depositPlayer(player, (double)rewardAmount);
         if (messageEnabled.equals("true")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.message").replace("%money%", String.valueOf(rewardAmount)).replace("%randomcolor%", this.getRandomColorCode())));
         }

         if (actionbarEnabled.equals("true")) {
            String message = ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("messages.actionbar").replace("%money%", String.valueOf(rewardAmount)).replace("%randomcolor%", this.getRandomColorCode()));
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
         }

         if (Sound != null) {
            player.playSound(player.getLocation(), Sound, 3.0F, 2.0F);
         }

         block.setType(Material.AIR);
         Bukkit.getScheduler().runTaskLater(Config.getPlugin(), () -> {
            if (block.getType() == Material.AIR) {
               block.setType(Material.SNOW_BLOCK);
            }

         }, (long)(Config.getConfig().getInt("respawn.delay_seconds") * 20));
      }

   }

   private String getRandomColorCode() {
      String[] colors = new String[]{"&a", "&b", "&c", "&d", "&e", "&6", "&9"};
      return colors[(new Random()).nextInt(colors.length)];
   }

   private boolean isPlayerInSnowEventRegion(Player player) {
      Location playerLocation = player.getLocation();
      BlockVector3 blockVector3 = BukkitAdapter.asBlockVector(playerLocation);
      ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(playerLocation.getWorld())).getApplicableRegions(blockVector3);
      Iterator var5 = regions.iterator();

      ProtectedRegion region;
      do {
         if (!var5.hasNext()) {
            return false;
         }

         region = (ProtectedRegion)var5.next();
      } while(!region.getId().equals(Config.getConfig().getString("worldguard.region")));

      return true;
   }
}
