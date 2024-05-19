package shauryagupta06.getping;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class GetPing extends PluginBase {

    @Override
    public void onEnable() {
        this.getLogger().info("GetPing enabled!");
        this.getServer().getCommandMap().register("ping", new PluginCommand<>("ping", this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ping")) {
            if (args.length == 0) {
                // Self ping
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    int ping = player.getPing();
                    sender.sendMessage(getPingMessage(player.getName(), ping));
                } else {
                    sender.sendMessage(TextFormat.RED + "This command can only be used in-game.");
                }
            } else if (args.length == 1) {
                // Other player's ping
                Player target = this.getServer().getPlayer(args[0]);
                if (target != null) {
                    int ping = target.getPing();
                    sender.sendMessage(getPingMessage(target.getName(), ping));
                } else {
                    this.getServer().broadcastMessage(TextFormat.RED + "Player not found.");
                }
            } else {
                sender.sendMessage(TextFormat.RED + "Usage: /ping [player]");
            }
            return true;
        }
        return false;
    }

    private String getPingMessage(String playerName, int ping) {
        String color;
        if (ping > 0 && ping < 90) {
            color = TextFormat.GREEN;
        } else if (ping >= 90 && ping < 180) {
            color = TextFormat.YELLOW;
        } else {
            color = TextFormat.RED;
        }
        return color + playerName + "'s ping: " + ping + "ms";
    }
}
