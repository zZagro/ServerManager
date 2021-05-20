package eu.zzagro.servermanager.commands;

import de.ancash.yaml.configuration.file.YamlFile;
import eu.zzagro.servermanager.ServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCmd implements CommandExecutor {

    private final ServerManager plugin;

    public HelpCmd(ServerManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        if (config.getBoolean("CustomHelpCommand")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (command.getName().equalsIgnoreCase("help")) {
                    Integer pages = config.getInt("HelpCommand.pages");
                    if (args.length < 1) {
                        String page1 = config.getString("HelpCommand.page1");
                        page1 = page1.replace("[", "");
                        page1 = page1.replace("]", "");
                        page1 = page1.replaceAll(", ", "\n");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Help Page (&a1&6/" + "&a" + pages + "&6)"));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', page1));
                    } else if (args.length == 1) {
                        getHelpPage(args[0], player, pages);
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /help");
                    }
                }
            } else {
                sender.sendMessage(prefix + ChatColor.RED + "You have to be a Player to use this command!");
            }
        } else {
            sender.sendMessage(prefix + ChatColor.RED + "The help command is disabled! Contact and Admin if you think this is unintentionally!!");
        }
        return false;
    }

    public void getHelpPage(String pageNumber, CommandSender sender, int pages) {
        YamlFile config = plugin.config;
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix") + " ");
        if (!(config.contains("HelpCommand.page" + pageNumber))) {
            sender.sendMessage(prefix + ChatColor.RED + "There are only " + ChatColor.GOLD + pages + ChatColor.RED + " Help pages!");
        } else {
            String page = config.getString("HelpCommand.page" + pageNumber);
            page = page.replace("[", "");
            page = page.replace("]", "");
            page = page.replaceAll(", ", "\n");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Help Page (" + "&a" + pageNumber + "&6/" + "&a" + pages + "&a)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', page));
        }
    }
}
