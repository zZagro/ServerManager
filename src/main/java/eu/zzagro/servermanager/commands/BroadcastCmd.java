package eu.zzagro.servermanager.commands;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCmd implements CommandExecutor {

    private final ServerManager plugin;

    public BroadcastCmd(ServerManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(config.getString("Permissions.Broadcast"))) {
                if (command.getName().equalsIgnoreCase("broadcast")) {
                    if (args.length < 1) {
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /broadcast <message>");
                    } else {
                        StringBuilder x = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            x.append(args[i] + " ");
                        }
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lBroadcast " + ChatColor.GRAY + ">> " + "&f" + x.toString().trim()));
                        }
                    }
                }
            }
        } else {
            if (command.getName().equalsIgnoreCase("broadcast")) {
                if (args.length < 1) {
                    sender.sendMessage(prefix + ChatColor.RED + "Usage: /broadcast <message>");
                } else {
                    StringBuilder x = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        x.append(args[i] + " ");
                    }

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lBroadcast " + ChatColor.GRAY + ">> " + "&f" + x.toString().trim()));
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lBroadcast " + ChatColor.GRAY + ">> " + "&f" + x.toString().trim()));
                }
            }
        }
        return false;
    }
}
