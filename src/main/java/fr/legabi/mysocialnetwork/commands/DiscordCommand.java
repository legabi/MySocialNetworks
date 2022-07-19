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


public class DiscordCommand implements CommandExecutor {
    Main main;
    public DiscordCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final File file = new File(main.getDataFolder(), "/config.yml");

        if (!main.config.getBoolean("discord.enable")) {
            sender.sendMessage(main.getString("notEnabled"));
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is executable only by players");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (player.hasPermission("mycontact.discord")) {
                TextComponent msg = new TextComponent(main.getString("discord.message")
                        .replace("&", "§"));
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, main.getString("discord.link")));
                player.spigot().sendMessage(msg);
            } else {
                player.sendMessage(main.getString("notHavePermission"));
            }
        }


        if (args.length >= 1) {
            if (player.hasPermission("mycontact.discord.config")) {
                if (args.length >= 3) {
                    if (args[0].equalsIgnoreCase("config")) {
                        if (args[1].equalsIgnoreCase("link")) {
                            main.config.set("discord.link", args[2]);
                        } else if (args[1].equalsIgnoreCase("message")) {
                            String message = "";
                            for (int i=2; i<args.length; i++) {
                                if (i == 2) {
                                    message = message + args[i];
                                }  else {
                                    message = message + " " + args[i];
                                }
                            }
                            main.config.set("discord.message", message);
                        }

                        try {
                            main.config.save(file);
                            main.config.load(file);
                            player.sendMessage("§aConfiguration update !");
                        } catch (IOException | InvalidConfigurationException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        if (player.hasPermission("mycontact.discord")) {
                            TextComponent msg = new TextComponent(main.getString("discord.message")
                                    .replace("&", "§"));
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, main.getString("discord.link")));
                            player.spigot().sendMessage(msg);
                        } else {
                            player.sendMessage(main.getString("notHavePermission"));
                        }
                    }
                } else {
                    if (args[0].equalsIgnoreCase("disable")) {
                        main.config.set("discord.enable", false);
                        try {
                            main.config.save(file);
                            main.config.load(file);
                            player.sendMessage("§aThis command is now disabled");
                        } catch (IOException | InvalidConfigurationException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        player.sendMessage("§c/discord config link/message <link>");
                    }

                }
            } else {
                player.sendMessage(main.getString("notHavePermission"));
            }
        }
        return true;
    }
}
