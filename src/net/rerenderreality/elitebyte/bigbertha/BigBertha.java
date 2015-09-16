package net.rerenderreality.elitebyte.bigbertha;

import java.util.HashMap;
import java.util.Map;

import net.rerenderreality.elitebyte.main.RRRPMainClass;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class BigBertha implements Listener {
	public Map<Player, Integer> kickWarnings = new HashMap<Player, Integer>();
	public final static String bb = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA
			+ "" + ChatColor.GOLD + "Big" + ChatColor.BOLD + ""
			+ ChatColor.DARK_RED + "Bertha" + ChatColor.DARK_GRAY + "]"
			+ ChatColor.WHITE;

	private static RRRPMainClass plugin;
	private final BigBerthaHandler bbh = new BigBerthaHandler(plugin);

	public BigBertha(RRRPMainClass plugin) {
		BigBertha.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void chatEvent(final PlayerChatEvent event) throws Exception {
		bbh.bigBerthaMsgHandle(event.getMessage(), event.getPlayer(), event);

	}

}
