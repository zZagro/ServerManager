package eu.zzagro.servermanager.commands;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCmd implements CommandExecutor {

    private final ServerManager plugin;

    public KickCmd(ServerManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(config.getString("Permissions.Kick"))) {
                if (command.getName().equalsIgnoreCase("kick")) {
                    if (args.length < 2) {
                        sender.sendMessage(prefix + ChatColor.RED + "Usage: /kick <player> <reason>");
                    } else {
                        Player target = Bukkit.getPlayerExact(args[0]);

                        if (target == null) {
                            sender.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.RED + " is not online!");
                            return true;
                        }

                        StringBuilder x = new StringBuilder();

                        for (int i = 1; i < args.length; i++) {
                            x.append(args[i]+" ");
                        }

                        String kicker = player.getName();

                        target.kickPlayer(ChatColor.RED + "You have been kicked from the server!\nBy: " + kicker + "\nReason: " + x.toString().trim());
                        sender.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.GREEN + " got kicked from the server!");
                    }
                }
            }
        } else {
            if (command.getName().equalsIgnoreCase("kick")) {
                if (args.length < 2) {
                    sender.sendMessage(prefix + ChatColor.RED + "Usage: /kick <player> <reason>");
                } else {
                    Player target = Bukkit.getPlayerExact(args[0]);

                    if (target == null) {
                        sender.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.RED + " is not online!");
                        return true;
                    }

                    StringBuilder x = new StringBuilder();

                    for (int i = 1; i < args.length; i++) {
                        x.append(args[i]+" ");
                    }

                    String kicker = "Server";

                    target.kickPlayer(ChatColor.RED + "You have been kicked from the server!\nBy: " + kicker + "\nReason: " + x.toString().trim());
                    sender.sendMessage(prefix + ChatColor.GOLD + args[0] + ChatColor.GREEN + " got kicked from the server!");
                }
            }
        }
        return false;
    }
}
