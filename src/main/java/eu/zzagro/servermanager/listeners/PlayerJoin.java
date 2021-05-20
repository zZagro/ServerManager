package eu.zzagro.servermanager.listeners;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoin implements Listener {

    private final ServerManager plugin;

    public PlayerJoin(ServerManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");

        Player player = e.getPlayer();

        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString("JoinMessage")));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        YamlFile bannedConf = plugin.bannedConf;
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");

        Player player = e.getPlayer();

        if (bannedConf.getConfigurationSection("banned_players") != null) {
            for (String section : bannedConf.getConfigurationSection("banned_players").getKeys(false)) {
                if (section.contains(player.getUniqueId().toString())) {
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You are banned from the server!\nBy: " + bannedConf.getString("banned_players." + player.getUniqueId() + ".banner") + "\nReason: " + bannedConf.getString("banned_players." + player.getUniqueId() + ".reason"));
                }
            }
        }
    }
}
