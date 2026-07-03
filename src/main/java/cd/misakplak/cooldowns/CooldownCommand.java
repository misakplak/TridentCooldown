package cd.misakplak.cooldowns;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CooldownCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("cooldown.reload") && !(sender instanceof Player player)) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§c/cooldown reload");
            return true;
        }
switch (args[0]) {
    case "reload":
        Cooldowns.getInstance().reloadConfig();
        player.sendMessage("§aConfig reloaded!");
        return true;
}

        return true;
    }
}
