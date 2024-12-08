package surc.simbay.snoweventremastered.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReloadCompleter implements TabCompleter {
   @Nullable
   public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
      if (strings.length == 1) {
         List<String> allOptions = Arrays.asList("reload", "join", "leave", "toggle");
         List<String> filteredOptions = new ArrayList();
         Iterator var7 = allOptions.iterator();

         while(var7.hasNext()) {
            String option = (String)var7.next();
            if (option.startsWith(strings[0].toLowerCase())) {
               filteredOptions.add(option);
            }
         }

         return filteredOptions;
      } else if (strings.length >= 2 && strings[0].equalsIgnoreCase("reload")) {
         return Arrays.asList("");
      } else if (strings.length >= 2 && strings[0].equalsIgnoreCase("join")) {
         return Arrays.asList("");
      } else if (strings.length >= 2 && strings[0].equalsIgnoreCase("leave")) {
         return Arrays.asList("");
      } else {
         return strings.length >= 2 && strings[0].equalsIgnoreCase("toggle") ? Arrays.asList("") : null;
      }
   }
}
