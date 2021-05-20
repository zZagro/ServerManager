package eu.zzagro.servermanager.commands;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class UnbanCmd implements CommandExecutor {

    private final ServerManager plugin;

    public UnbanCmd(ServerManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlFile config = plugin.config;
        YamlFile bannedConf = plugin.bannedConf;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(config.getString("Permissions.Unban"))) {
                if (command.getName().equalsIgnoreCase("unban")) {
                    if (args.length != 1) {
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /unban <player>");
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        if (target == null) {
                            player.sendMessage("Null");
                        } else {
                            for (String section : bannedConf.getConfigurationSection("banned_players").getKeys(false)) {
                                if (section.contains(target.getUniqueId().toString())) {
                                    bannedConf.set("banned_players." + target.getUniqueId(), null);
                                    player.sendMessage(prefix + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " has been unbanned!");
                                    try {
                                        bannedConf.save();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } else {
            if (command.getName().equalsIgnoreCase("unban")) {
                if (args.length != 1) {
                    sender.sendMessage(prefix + ChatColor.RED + "Usage: /unban <player>");
                } else {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (bannedConf.getConfigurationSection("banned_players") != null) {
                        for (String section : bannedConf.getConfigurationSection("banned_players").getKeys(false)) {
                            if (section.contains(target.getUniqueId().toString())) {
                                bannedConf.set("banned_players." + target.getUniqueId(), null);
                                sender.sendMessage(prefix + ChatColor.GOLD + target + ChatColor.GREEN + " has been unbanned!");
                                try {
                                    bannedConf.save();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
