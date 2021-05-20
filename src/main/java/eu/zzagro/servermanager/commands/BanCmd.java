package eu.zzagro.servermanager.commands;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class BanCmd implements CommandExecutor {

    private final ServerManager plugin;

    public BanCmd(ServerManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlFile config = plugin.config;
        YamlFile bannedConf = plugin.bannedConf;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(config.getString("Permissions.Ban"))) {
                if (command.getName().equalsIgnoreCase("ban")) {
                    if (args.length < 2) {
                        sender.sendMessage(prefix + ChatColor.RED + "Usage: /ban <player> <reason>");
                    } else {
                        Player target = Bukkit.getPlayerExact(args[0]);

                        if (target == null) {
                            OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[0]);

                            if (offlineTarget == null) {
                                player.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.RED + " does not exist!");
                                return true;
                            }

                            StringBuilder x = new StringBuilder();

                            for (int i = 1; i < args.length; i++) {
                                x.append(args[i]+" ");
                            }

                            String banner = player.getName();

                            bannedConf.set("banned_players." + offlineTarget.getUniqueId() + ".name", offlineTarget.getName());
                            bannedConf.set("banned_players." + offlineTarget.getUniqueId() + ".banner", banner);
                            bannedConf.set("banned_players." + offlineTarget.getUniqueId() + ".reason", x.toString().trim());
                            player.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.GREEN + " got banned from the server!");
                            try {
                                bannedConf.save();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return true;
                        }

                        StringBuilder x = new StringBuilder();

                        for (int i = 1; i < args.length; i++) {
                            x.append(args[i]+" ");
                        }

                        String banner = player.getName();

                        target.kickPlayer(ChatColor.RED + "You have been banned from the server!\nBy: " + banner + "\nReason: " + x.toString().trim());
                        bannedConf.set("banned_players." + target.getUniqueId() + ".name", target.getName());
                        bannedConf.set("banned_players." + target.getUniqueId() + ".banner", banner);
                        bannedConf.set("banned_players." + target.getUniqueId() + ".reason", x.toString().trim());
                        player.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.GREEN + " got banned from the server!");
                        try {
                            bannedConf.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            if (command.getName().equalsIgnoreCase("ban")) {
                if (args.length < 2) {
                    sender.sendMessage(prefix + ChatColor.RED + "Usage: /ban <player> <reason>");
                } else {
                    Player target = Bukkit.getPlayerExact(args[0]);

                    if (target == null) {
                        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[0]);

                        if (offlineTarget == null) {
                            sender.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.RED + " does not exist!");
                            return true;
                        }

                        StringBuilder x = new StringBuilder();

                        for (int i = 1; i < args.length; i++) {
                            x.append(args[i]+" ");
                        }

                        String banner = sender.getName();

                        bannedConf.set("banned_players." + offlineTarget.getUniqueId() + ".name", offlineTarget.getName());
                        bannedConf.set("banned_players." + offlineTarget.getUniqueId() + ".banner", banner);
                        bannedConf.set("banned_players." + offlineTarget.getUniqueId() + ".reason", x.toString().trim());
                        sender.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.GREEN + " got banned from the server!");
                        try {
                            bannedConf.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }

                    StringBuilder x = new StringBuilder();

                    for (int i = 1; i < args.length; i++) {
                        x.append(args[i]+" ");
                    }

                    String banner = "Server";

                    target.kickPlayer(ChatColor.RED + "You have been banned from the server!\nBy: " + banner + "\nReason: " + x.toString().trim());
                    bannedConf.set("banned_players." + target.getUniqueId() + ".name", target.getName());
                    bannedConf.set("banned_players." + target.getUniqueId() + ".banner", banner);
                    bannedConf.set("banned_players." + target.getUniqueId() + ".reason", x.toString().trim());
                    sender.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.GREEN + " got banned from the server!");
                    try {
                        bannedConf.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
