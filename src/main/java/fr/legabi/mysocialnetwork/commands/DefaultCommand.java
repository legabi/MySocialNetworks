package fr.legabi.mysocialnetwork.commands;

import fr.legabi.mysocialnetwork.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DefaultCommand implements CommandExecutor {

    Main main;

    public DefaultCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("mysocialmedia.admin")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please use /" + command.getName() + " <arg>");
            } else {
                if (args[0].equalsIgnoreCase("reload")) {
                    main.config = Main.getFileConfiguration("config");
                    sender.sendMessage(ChatColor.GREEN + "Configuration reloaded !");
                }
            }
        } else {
            sender.sendMessage(main.getString("notHavePermission"));
        }
        return true;
    }
}
