package eu.zzagro.servermanager;

import de.ancash.misc.FileUtils;
import de.ancash.yaml.configuration.file.YamlFile;
import de.ancash.yaml.exceptions.InvalidConfigurationException;
import eu.zzagro.servermanager.commands.*;
import eu.zzagro.servermanager.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ServerManager extends JavaPlugin {

    public YamlFile config = new YamlFile(new File("plugins/ServerManager/config.yml"));
    public YamlFile bannedConf = new YamlFile(new File("plugins/ServerManager/banned.yml"));

    @Override
    public void onEnable() {
        if (!(Bukkit.getPluginManager().isPluginEnabled("ILibrary"))) {
            System.out.println("--------------------------------------");
            System.out.println("Plugin ILibrary not found!");
            System.out.println("Install ILibrary from the link on the Plugin download page to use the Plugin!");
            System.out.println("--------------------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            getCommand("ban").setExecutor(new BanCmd(this));
            getCommand("kick").setExecutor(new KickCmd(this));
            getCommand("unban").setExecutor(new UnbanCmd(this));
            getCommand("broadcast").setExecutor(new BroadcastCmd(this));
            getCommand("help").setExecutor(new HelpCmd(this));
            getCommand("manager").setExecutor(new ManagerCmd(this));
            getCommand("gm").setExecutor(new GmCmd(this));

            getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
            getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);

            permissionAdd();
            loadFiles();
        }
    }

    @Override
    public void onDisable() {
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFiles() {
        try {
            if (!(config.exists())) {
                FileUtils.copyInputStreamToFile(getResource("config.yml"), new File("plugins/ServerManager/config.yml"));
            }
            config.createOrLoad();
            bannedConf.createOrLoad();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void permissionAdd() {
        PluginManager pm =  getServer().getPluginManager();
        Set<Permission> permissions = pm.getPermissions();

        Permission ban = new Permission(getConfig().getString("Permissions.Ban"));
        if (!permissions.contains(ban)) {
            pm.addPermission(ban);
        }

        Permission unban = new Permission(getConfig().getString("Permissions.Unban"));
        if (!permissions.contains(unban)) {
            pm.addPermission(unban);
        }

        Permission kick = new Permission(getConfig().getString("Permissions.Kick"));
        if (!permissions.contains(kick)) {
            pm.addPermission(kick);
        }

        Permission broadcast = new Permission(getConfig().getString("Permissions.Broadcast"));
        if (!permissions.contains(broadcast)) {
            pm.addPermission(broadcast);
        }

        Permission gm = new Permission(getConfig().getString("Permissions.Gm"));
        if (!permissions.contains(gm)) {
            pm.addPermission(gm);
        }

        Permission playersgui = new Permission(getConfig().getString("Permissions.PlayersGUI"));
        if (!permissions.contains(playersgui)) {
            pm.addPermission(playersgui);
        }

        Permission manager = new Permission(getConfig().getString("Permissions.Manager"));
        if (!permissions.contains(manager)) {
            pm.addPermission(manager);
        }
    }
}
