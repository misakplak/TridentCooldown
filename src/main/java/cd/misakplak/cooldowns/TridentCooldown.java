package cd.misakplak.cooldowns;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TridentCooldown implements Listener {

    @EventHandler
    public void onTridentUse(PlayerRiptideEvent e) {

                int seconds = Cooldowns.getInstance().getConfig().getInt("trident-cooldown");
                ItemStack item = e.getItem();
                Player player = e.getPlayer();

                if (Cooldowns.getInstance().getCooldownPlayers().containsKey(player.getUniqueId())) {
                    return;
                }

        Cooldowns.getInstance().getCooldownPlayers().put(e.getPlayer().getUniqueId(), e.getPlayer().getUniqueId());
        player.setCooldown(item.getType(), seconds * 20);
        StartCooldownTimer(e.getPlayer());
        e.getPlayer().sendMessage("§8You are on cooldown!");

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (Cooldowns.getInstance().getCooldownPlayers().containsKey(e.getPlayer().getUniqueId())) {
            Cooldowns.getInstance().getCooldownPlayers().remove(e.getPlayer().getUniqueId());
        }
    }


    public void StartCooldownTimer(Player player) {

        int maxTime = Cooldowns.getInstance().getConfig().getInt("trident-cooldown");

        BossBar cooldown = BossBar.bossBar(
                Component.text(""),
                1.0f,
                BossBar.Color.GREEN,
                BossBar.Overlay.PROGRESS
        );
        boolean activate = Cooldowns.getInstance().getConfig().getBoolean("send-trident-cooldowntimetobossbar");
if (activate == true) {
    player.showBossBar(cooldown);
}


        new BukkitRunnable() {

            int cdTime = maxTime;

            @Override
            public void run() {

                if (!Cooldowns.getInstance().getCooldownPlayers().containsKey(player.getUniqueId())) {
                    player.hideBossBar(cooldown);
                    cancel();
                    return;
                }

                if (cdTime <= 0) {
                    Cooldowns.getInstance().getCooldownPlayers().remove(player.getUniqueId());
                    player.hideBossBar(cooldown);
                    cancel();
                    return;
                }

                cooldown.name(Component.text(
                        "§3You are on cooldown for " + cdTime + " more seconds"
                ));

                cooldown.progress(cdTime / (float) maxTime);

                cdTime--;
            }

        }.runTaskTimer(Cooldowns.getInstance(), 0L, 20L);
    }
}
