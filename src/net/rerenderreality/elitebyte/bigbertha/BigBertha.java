package net.rerenderreality.elitebyte.bigbertha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rerenderreality.elitebyte.main.RRRPMainClass;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import pw.prok.imagine.util.Array;

@SuppressWarnings("deprecation")
public class BigBertha implements Listener {
	public Map<Player, Integer> kickWarnings = new HashMap<Player, Integer>();
	public static String bb = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "" + ChatColor.GOLD + "Big" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "Bertha" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE;
	List<String> bbAliases = Array.asList("bb ", "berty ", "biggy ", "bertha ", "biggie ", "bigbertha ", "big bertha ");
	List<String> tps = Array.asList("tps", "ticks per second");
	List<String> feeling = Array.asList("feeling");
	List<String> cussWords = Array.asList("fuck", "fk", "ass", "arse", "dick", "dik", "asshole", "bastard", "bitch", "bollocks", "fuc", "fucker", "shit", "goddamn", "damn", "cunt", "kunt", "whore", "cock", "retard");
	List<String> liked = Array.asList("favorite", "fav", "loved", "like", "dearest");
	List<String> song = Array.asList("song", "music", "jam");
	List<String> loc = Array.asList("location", "dimension", "coords", "loc", "position", "world", "where is");
	List<String> kick = Array.asList("kick", "boot", "remove");		
	List<String> mobs = Array.asList("mobs", "monsters", "entities", "fucken sacks of shit");
																	//Zeph made me ^ 
	List<String> butcher = Array.asList("butcher");
	List<String> kill = Array.asList("execute", "kill", "eliminate", "murder", "exterminate", "destroy", "obliterate", "void");
	List<String> ban = Array.asList("ban", "exile");
	
	private static RRRPMainClass plugin;
	private BigBerthaHandler bbh = new BigBerthaHandler(plugin);
	
	public BigBertha(RRRPMainClass plugin) {
		BigBertha.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void chatEvent(final PlayerChatEvent event) throws Exception {
			bbh.bigBerthaMsgHandle(event.getMessage(), event.getPlayer(), event);

	  	} 
	

	
	
}
