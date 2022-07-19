package fr.legabi.mysocialnetwork;

import fr.legabi.mysocialnetwork.commands.*;
import fr.legabi.mysocialnetwork.utils.Metrics;
import fr.legabi.mysocialnetwork.utils.UpdateChecker;
import fr.legabi.mysocialnetwork.utils.Utf8YamlConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class Main extends JavaPlugin implements Listener {

    private static Plugin plugin;
    public FileConfiguration config;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 15835);

        plugin = Bukkit.getPluginManager().getPlugin(getDescription().getName());
        Bukkit.getConsoleSender().sendMessage("\n" + getDescription().getPrefix() + ": §aEnable " + getDescription().getName() +
                " v" + getDescription().getVersion() + "\n§r" +
                getDescription().getPrefix() + ": §a" + getDescription().getDescription() + "\n§r" +
                getDescription().getPrefix() + ": §aView author site: " + getDescription().getWebsite()
        );

        new UpdateChecker(this, 103497).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });

        config = getFileConfiguration("config");
        getCommand("discord").setExecutor(new DiscordCommand(this));
        getCommand("youtube").setExecutor(new YouTubeCommand(this));
        getCommand("tiktok").setExecutor(new TikTokCommand(this));
        getCommand("twitter").setExecutor(new TwitterCommand(this));
        getCommand("site").setExecutor(new SiteCommand(this));
        getCommand("mysocialnetworks").setExecutor(new DefaultCommand(this));

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "\n           __  __        _____ __  __          \n" +
                "          |  \\/  |      / ____|  \\/  |         \n" +
                "  ______  | \\  / |_   _| (___ | \\  / |  ______ \n" +
                " |______| | |\\/| | | | |\\___ \\| |\\/| | |______|\n" +
                "          | |  | | |_| |____) | |  | |         \n" +
                "          |_|  |_|\\__, |_____/|_|  |_|         \n" +
                "                   __/ |                       \n" +
                "                  |___/                        ");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("\n" + getDescription().getPrefix() + ": §aDisable " + getDescription().getName() +
                " v" + getDescription().getVersion() + "\n§r" +
                getDescription().getPrefix() + ": §a" + getDescription().getDescription() + "\n§r" +
                getDescription().getPrefix() + ": §aView author site: " + getDescription().getWebsite()
        );

        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "\n           __  __        _____ __  __          \n" +
                "          |  \\/  |      / ____|  \\/  |         \n" +
                "  ______  | \\  / |_   _| (___ | \\  / |  ______ \n" +
                " |______| | |\\/| | | | |\\___ \\| |\\/| | |______|\n" +
                "          | |  | | |_| |____) | |  | |         \n" +
                "          |_|  |_|\\__, |_____/|_|  |_|         \n" +
                "                   __/ |                       \n" +
                "                  |___/                        ");
    }

    public String getString(String s) {
        return config.getString(s).replace("&", "§");
    }

    public static FileConfiguration getFileConfiguration(String fileName) {

        File file = new File("plugins/" + plugin.getName() + "/" + fileName + ".yml");
        FileConfiguration fileConfiguration = new Utf8YamlConfiguration();

        try {
            fileConfiguration.load(file);
            return fileConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().info("Generating fresh configuration file: " + fileName + ".yml");
        }

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                InputStream in = plugin.getResource(fileName + ".yml");
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
                out.close();
                in.close();
            }
            fileConfiguration.load(file);
        } catch(IOException|InvalidConfigurationException ex) {
            plugin.getLogger().severe("Plugin unable to write configuration file " + fileName + ".yml!");
            plugin.getLogger().severe("Disabling...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            ex.printStackTrace();
        }

        return fileConfiguration;
    }
}
