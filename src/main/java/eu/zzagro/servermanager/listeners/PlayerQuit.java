package eu.zzagro.servermanager.listeners;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final ServerManager plugin;

    public PlayerQuit(ServerManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        YamlFile config = plugin.config;

        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', config.getString("QuitMessage")));
    }
}
