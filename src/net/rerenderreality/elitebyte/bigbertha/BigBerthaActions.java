package net.rerenderreality.elitebyte.bigbertha;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BigBerthaActions {

	/*
	 * BigBerthaActions class is used to execute it's different actions Actions
	 * inculde : Broadcast, PlayerJoin, more2come....
	 */

	private final String elitePrefix = ChatColor.BOLD + "" + ChatColor.BLACK
			+ "[" + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Dev"
			+ ChatColor.BOLD + ChatColor.DARK_GRAY + "God" + ChatColor.BOLD
			+ "" + ChatColor.WHITE + "-" + ChatColor.BOLD + ""
			+ ChatColor.DARK_RED + "EliteByte" + ChatColor.BOLD + ""
			+ ChatColor.BLACK + "]" + ChatColor.WHITE;
	private final String ocePrefix = ChatColor.RED + "" + ChatColor.BOLD
			+ "SystemAdmin" + ChatColor.GRAY + "-" + ChatColor.BLUE + "Ocelot";

	private final String kiloPrefix = ChatColor.DARK_AQUA + "" + ChatColor.BOLD
			+ "Co-Owner " + ChatColor.GOLD + "Kilo";
	private final String toastPrefix = ChatColor.DARK_RED + "" + ChatColor.BOLD
			+ "Owner " + ChatColor.WHITE + "" + ChatColor.BOLD + "Toast";

	/*
	 * Broadcasts the inputed String with [BigBertha] prefix.
	 */
	public void berthaBroadcast(String msg) {
		Bukkit.broadcastMessage(BigBertha.bb + " " + msg);
	}

	/*
	 * Broadcasts the inputed String with [DevGod-Elite] prefix.
	 */
	public void eliteBroadcast(String msg) {
		Bukkit.broadcastMessage(elitePrefix + " " + msg);
	}

	public void oceBroadcast(String msg) {
		Bukkit.broadcastMessage(ocePrefix + " " + msg);
	}

	public void kiloBroadcast(String msg) {
		Bukkit.broadcastMessage(kiloPrefix + " " + msg);
	}

	public void toastBroadcast(String msg) {
		Bukkit.broadcastMessage(toastPrefix + " " + msg);
	}

}
