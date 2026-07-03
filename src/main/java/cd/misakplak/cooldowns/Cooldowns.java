package cd.misakplak.cooldowns;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

public final class Cooldowns extends JavaPlugin {

private static Cooldowns instance;

    private HashMap<UUID, UUID> cooldownPlayers = new HashMap<>();

    public HashMap<UUID, UUID> getCooldownPlayers() {
        return cooldownPlayers;
    }
    public static Cooldowns getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        if (!Files.exists(getDataFolder().toPath())) {}

        getServer().getPluginManager().registerEvents(new TridentCooldown(), this);
        getCommand("cooldown").setExecutor(new CooldownCommand());

    }

    @Override
    public void onDisable() {
        cooldownPlayers.clear();
        instance = null;
        // Plugin shutdown logic
    }
}
