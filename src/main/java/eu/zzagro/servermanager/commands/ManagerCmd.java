package eu.zzagro.servermanager.commands;

import de.ancash.yaml.configuration.file.YamlFile;
import de.ancash.yaml.exceptions.InvalidConfigurationException;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ManagerCmd implements CommandExecutor {

    private final ServerManager plugin;

    public ManagerCmd(ServerManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        String managerHelp = prefix + ChatColor.GREEN + "All Commands:\n/Ban <player> <reason> " + ChatColor.GOLD + "- Ban a Player from your server" + ChatColor.GREEN + "\n/Broadcast <message> " + ChatColor.GOLD + "- Send a Highlighted message to all Online Players" + ChatColor.GREEN + "\n/Help " + ChatColor.GOLD + "- Shows the Help command you can setup in the config.yml" + ChatColor.GREEN + "\n/Kick <player> <reason> " + ChatColor.GOLD + "- Kick a Player from your server" + ChatColor.GREEN + "\n/Manager " + ChatColor.GOLD + "- Shows all manager (admin) commands" + ChatColor.GREEN + "\n/Unban <player> " + ChatColor.GOLD + "- Unban a banned Player from your server";
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(config.getString("Permissions.Manager"))) {
                if (command.getName().equalsIgnoreCase("Manager")) {
                    if (args.length == 0) {
                        player.sendMessage(prefix + ChatColor.GREEN + "Available Commands:\n/manager help " + ChatColor.GOLD + "- Shows all available commands" + ChatColor.GREEN + "\n/manager reload " + ChatColor.GOLD + "- Reload the config.yml");
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("reload")) {
                            try {
                                config.load();
                            } catch (InvalidConfigurationException | IOException e) {
                                e.printStackTrace();
                            }
                            player.sendMessage(prefix + ChatColor.GREEN + "Config reloaded");
                        } else if (args[0].equalsIgnoreCase("help")) {
                            player.sendMessage(managerHelp);
                        }
                    }
                }
            }
        } else {
            if (command.getName().equalsIgnoreCase("Manager")) {
                if (args.length == 0) {
                    sender.sendMessage(prefix + ChatColor.GREEN + "Available Commands:\n/manager help " + ChatColor.GOLD + "- Shows all available commands" + ChatColor.GREEN + "\n/manager reload " + ChatColor.GOLD + "- Reload the config.yml");
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        try {
                            config.load();
                        } catch (InvalidConfigurationException | IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage(prefix + ChatColor.GREEN + "Config reloaded");
                    } else if (args[0].equalsIgnoreCase("help")) {
                        sender.sendMessage(managerHelp);
                    }
                }
            }
        }
        return false;
    }
}
