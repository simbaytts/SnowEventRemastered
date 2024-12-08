package surc.simbay.snoweventremastered;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

public class SnowJobManager {
   private static Set<Player> hiredPlayers = new HashSet();

   public static void hirePlayer(Player player) {
      hiredPlayers.add(player);
   }

   public static void firePlayer(Player player) {
      hiredPlayers.remove(player);
   }

   public static boolean isPlayerHired(Player player) {
      return hiredPlayers.contains(player);
   }
}
