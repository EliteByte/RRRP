package net.rerenderreality.elitebyte.bigbertha;

import org.bukkit.Bukkit;

public class BigBerthaActions {
	
	/*
	 * BigBerthaActions class is used
	 * to execute it's different actions 
	 * Actions inculde : Broadcast, PlayerJoin, more2come....
	 */
	
	public static void berthaBroadcast(String msg) {
		Bukkit.broadcastMessage(BigBertha.bb + " " + msg);
	}
	
}
