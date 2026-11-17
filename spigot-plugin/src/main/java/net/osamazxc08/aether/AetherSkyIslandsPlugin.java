package net.osamazxc08.aether;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class AetherSkyIslandsPlugin extends JavaPlugin {

    private static final String PREFIX = ChatColor.GOLD + "[Aether] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        getLogger().info("Aether Sky Islands enabled.");
        getServer().getPluginManager().registerEvents(new IslandWorldListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("aether")) {
            return false;
        }

        if (args.length == 0) {
            sendInfo(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            sendInfo(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("regen")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(PREFIX + "This command can only be used by a player.");
                return true;
            }
            Player player = (Player) sender;
            Block target = player.getTargetBlock(null, 64);
            if (target == null || target.getType() == Material.AIR) {
                sender.sendMessage(PREFIX + "Look at a block first.");
                return true;
            }
            new IslandRegenerator(this).regenerateAt(target.getLocation(), 12, 6);
            sender.sendMessage(PREFIX + "Generated a floating island cluster around the targeted block.");
            return true;
        }

        sender.sendMessage(PREFIX + "Unknown subcommand. Use /aether info");
        return true;
    }

    private void sendInfo(CommandSender sender) {
        sender.sendMessage(PREFIX + "Aether Sky Islands");
        sender.sendMessage(ChatColor.GRAY + "Commands:");
        sender.sendMessage(ChatColor.GRAY + "- /aether info" + ChatColor.RESET + " -> plugin information");
        sender.sendMessage(ChatColor.GRAY + "- /aether regen" + ChatColor.RESET + " -> create a floating island around your target block");
    }

    static Random worldRandom(long worldSeed, int x, int z) {
        long mixed = worldSeed ^ ((long) x * 341873128712L) ^ ((long) z * 132897987541L);
        mixed ^= mixed >>> 30;
        mixed *= 0xbf58476d1ce4e5b9L;
        mixed ^= mixed >>> 27;
        mixed *= 0x94d049bb133111ebL;
        mixed ^= mixed >>> 31;
        return new Random(Math.abs(mixed));
    }
}
