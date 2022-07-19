package fr.legabi.mysocialnetwork.commands;


import fr.legabi.mysocialnetwork.Main;
import fr.legabi.mysocialnetwork.utils.Utf8YamlConfiguration;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;


public class YouTubeCommand implements CommandExecutor {
    Main main;
    public YouTubeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final File file = new File(main.getDataFolder(), "/config.yml");

        if (!main.config.getBoolean("youtube.enable")) {
            sender.sendMessage(main.getString("notEnabled"));
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is executable only by players");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (player.hasPermission("mycontact.youtube")) {
                TextComponent msg = new TextComponent(main.getString("youtube.message")
                        .replace("&", "§"));
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, main.getString("youtube.link")));
                player.spigot().sendMessage(msg);
            } else {
                player.sendMessage(main.getString("notHavePermission"));
            }
        }


        if (args.length >= 1) {
            if (player.hasPermission("mycontact.youtube.config")) {
                if (args.length >= 3) {
                    if (args[0].equalsIgnoreCase("config")) {
                        if (args[1].equalsIgnoreCase("link")) {
                            main.config.set("youtube.link", args[2]);
                        } else if (args[1].equalsIgnoreCase("message")) {
                            String message = "";
                            for (int i=2; i<args.length; i++) {
                                if (i == 2) {
                                    message = message + args[i];
                                }  else {
                                    message = message + " " + args[i];
                                }
                            }
                            main.config.set("youtube.message", message);
                        }

                        try {
                            main.config.save(file);
                            main.config.load(file);
                            player.sendMessage("§aConfiguration update !");
                        } catch (IOException | InvalidConfigurationException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        if (player.hasPermission("mycontact.youtube")) {
                            TextComponent msg = new TextComponent(main.getString("youtube.message")
                                    .replace("&", "§"));
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, main.getString("youtube.link")));
                            player.spigot().sendMessage(msg);
                        } else {
                            player.sendMessage(main.getString("notHavePermission"));
                        }
                    }
                } else {
                    if (args[0].equalsIgnoreCase("disable")) {
                        main.config.set("youtube.enable", false);
                        try {
                            main.config.save(file);
                            main.config.load(file);
                            player.sendMessage("§aThis command is now disabled");
                        } catch (IOException | InvalidConfigurationException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        player.sendMessage("§c/youtube config link/message <link>");
                    }

                }
            } else {
                player.sendMessage(main.getString("notHavePermission"));
            }
        }
        return true;
    }
}
