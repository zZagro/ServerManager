package eu.zzagro.servermanager.commands;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmCmd implements CommandExecutor {

    private final ServerManager plugin;

    public GmCmd(ServerManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(config.getString("Permissions.Gm"))) {
                if (command.getName().equalsIgnoreCase("gm")) {
                    if (args.length == 1) {
                        switch (args[0]) {
                            case "0":
                                if (!(player.getGameMode().equals(GameMode.SURVIVAL))) {
                                    player.setGameMode(GameMode.SURVIVAL);
                                    player.sendMessage(prefix + ChatColor.GREEN + "Your game mode is now set to " + ChatColor.GOLD + "Survival");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "Your game mode is already Survival");
                                }
                                break;
                            case "1":
                                if (!(player.getGameMode().equals(GameMode.CREATIVE))) {
                                    player.setGameMode(GameMode.CREATIVE);
                                    player.sendMessage(prefix + ChatColor.GREEN + "Your game mode is now set to " + ChatColor.GOLD + "Creative");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "Your game mode is already Creative");
                                }
                                break;
                            case "2":
                                if (!(player.getGameMode().equals(GameMode.ADVENTURE))) {
                                    player.setGameMode(GameMode.ADVENTURE);
                                    player.sendMessage(prefix + ChatColor.GREEN + "Your game mode is now set to " + ChatColor.GOLD + "Adventure");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "Your game mode is already Adventure");
                                }
                                break;
                            case "3":
                                if(!(player.getGameMode().equals(GameMode.SPECTATOR))) {
                                    player.setGameMode(GameMode.SPECTATOR);
                                    player.sendMessage(prefix + ChatColor.GREEN + "Your game mode is now set to " + ChatColor.GOLD + "Spectator");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "Your game mode is already Spectator");
                                }
                                break;
                            default:
                                player.sendMessage(prefix + ChatColor.RED + "Invalid game mode");
                        }
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target == null) {
                            player.sendMessage(prefix + ChatColor.RED + "Your target is offline or does not exist!");
                        } else {
                            switch (args[0]) {
                                case "0":
                                    if (!(target.getGameMode().equals(GameMode.SURVIVAL))) {
                                        target.setGameMode(GameMode.SURVIVAL);
                                        target.sendMessage(prefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " set your game mode to " + ChatColor.GOLD + "Survival");
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.GREEN + " game mode is now set to Survival");
                                    } else {
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.RED + " game mode is already Survival");
                                    }
                                    break;
                                case "1":
                                    if (!(target.getGameMode().equals(GameMode.CREATIVE))) {
                                        target.setGameMode(GameMode.CREATIVE);
                                        target.sendMessage(prefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " set your game mode to " + ChatColor.GOLD + "Creative");
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.GREEN + " game mode is now set to Creative");
                                    } else {
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.RED + " game mode is already Creative");
                                    }
                                    break;
                                case "2":
                                    if (!(target.getGameMode().equals(GameMode.ADVENTURE))) {
                                        target.setGameMode(GameMode.ADVENTURE);
                                        target.sendMessage(prefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " set your game mode to " + ChatColor.GOLD + "Adventure");
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.GREEN + " game mode is now set to Adventure");
                                    } else {
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.RED + " game mode is already Adventure");
                                    }
                                    break;
                                case "3":
                                    if (!(target.getGameMode().equals(GameMode.SPECTATOR))) {
                                        target.setGameMode(GameMode.SPECTATOR);
                                        target.sendMessage(prefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " set your game mode to " + ChatColor.GOLD + "Spectator");
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.GREEN + " game mode is now set to Spectator");
                                    } else {
                                        player.sendMessage(prefix + ChatColor.GOLD + target.getName() + "'s" + ChatColor.RED + " game mode is already Spectator");
                                    }
                                    break;
                                default:
                                    player.sendMessage(prefix + ChatColor.RED + "Invalid game mode");
                            }
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /gm <gamemode> <player>");
                    }
                }
            }
        }
        return false;
    }
}
