package net.rerenderreality.elitebyte.bigbertha;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BigBerthaActions {

	/*
	 * BigBerthaActions class is used to execute it's different actions Actions
	 * inculde : Broadcast, PlayerJoin, more2come....
	 */

	public static String elitePrefix = ChatColor.BOLD + "" + ChatColor.BLACK
			+ "[" + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Dev"
			+ ChatColor.BOLD + ChatColor.DARK_GRAY + "God" + ChatColor.BOLD
			+ "" + ChatColor.WHITE + "-" + ChatColor.BOLD + ""
			+ ChatColor.DARK_RED + "EliteByte" + ChatColor.BOLD + ""
			+ ChatColor.BLACK + "]" + ChatColor.WHITE;

	/*
	 * Broadcasts the inputed String with [BigBertha] prefix.
	 */
	public void berthaBroadcast(String msg) {
		Bukkit.broadcastMessage(BigBertha.bb + " " + msg);
	}

	/*
	 * Broadcasts the inputed String with [DevGod-Elite] prefix.
	 */
	public static void eliteBroadcast(String msg) {
		Bukkit.broadcastMessage(elitePrefix + " " + msg);
	}

}
