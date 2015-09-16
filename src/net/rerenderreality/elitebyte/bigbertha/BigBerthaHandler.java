package net.rerenderreality.elitebyte.bigbertha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rerenderreality.elitebyte.main.RRRPMainClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.PluginManager;

import com.earth2me.essentials.Essentials;

@SuppressWarnings("deprecation")
public class BigBerthaHandler {

	public final String bb = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + ""
			+ ChatColor.GOLD + "Big" + ChatColor.BOLD + "" + ChatColor.DARK_RED
			+ "Bertha" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE;
	Keyword kw = new Keyword();
	Map<Player, Integer> kickWarnings = new HashMap<Player, Integer>();
	private final BigBerthaActions bba = new BigBerthaActions();
	private static RRRPMainClass plugin;

	public BigBerthaHandler(RRRPMainClass plugin) {
		BigBerthaHandler.plugin = plugin;
	}

	public void bigBerthaMsgHandle(String msg, Player p, PlayerChatEvent event) {
		String msgg = msg.toLowerCase();

		PluginManager pm = plugin.getServer().getPluginManager();
		Essentials ess = (Essentials) pm.getPlugin("Essentials");

		if (kw.checkKeyWords(kw.bbAliases, null, msgg)) {
			// Check if you cussed to BB
			if (kw.checkKeyWords(kw.cussWords, null, msgg)) {
				kickHandler(p, event);

				// Check if keywords no cuss words were said.
			} else if (!kw.checkKeyWords(kw.cussWords, null, msgg)) {

				if (kw.checkKeyWords(kw.tps, null, msgg)) {
					double tps = ess.getTimer().getAverageTPS();
					bba.berthaBroadcast("The Server TPS is : "
							+ Math.round(tps));
				}

				// Check if keywords feeling were said.
				else if (kw.checkKeyWords(kw.feeling, null, msgg)) {
					int rand = plugin.randInt(1, 5);
					String str = "";

					bba.berthaBroadcast(feelingCheck(ess.getTimer()
							.getAverageTPS(), rand, str));
				}

				// Check if keywords song and a favorite were said.
				else if (kw.checkKeyWords(kw.song, kw.liked, msgg)) {
					bba.berthaBroadcast("My favorite "
							+ checkString(kw.song, msgg) + " is "
							+ ChatColor.UNDERLINE
							+ "https://www.youtube.com/watch?v=wk4ftn4PArg");
				}

				// Check if keywords loc and a player name were said.
				else if (plugin.containsPlayerNameBool(msgg)
						&& kw.checkKeyWords(kw.loc, null, msgg)) {
					if (plugin.isOnline(plugin.containsPlayerName(msgg)) != null) {
						bba.berthaBroadcast(bPlayerLoc(msgg, p));

					} else {
						bba.berthaBroadcast(ChatColor.DARK_RED
								+ " I'm sorry that player isn't online or his named is spelled incorrectly.");
					}

					// Check if keywords kick and a player name were said.
				} else if (kw.checkKeyWords(kw.kick, null, msgg)
						&& plugin.containsPlayerNameBool(msgg)) {
					if (p.hasPermission("rrrp.kick")) {
						if (plugin.isOnline(plugin.containsPlayerName(msgg)) != null) {
							Player target = plugin.isOnline(plugin
									.containsPlayerName(msgg));
							target.kickPlayer(p.getDisplayName()
									+ " says to GTFO!");

						} else {
							bba.berthaBroadcast(ChatColor.DARK_RED
									+ " I'm sorry that player isn't online or his named is spelled incorrectly.");
						}
					}
				}
				// Check if keywords kill and a player name were said.
				else if (kw.checkKeyWords(kw.kill, null, msgg)
						&& plugin.containsPlayerNameBool(msgg)) {
					if (p.hasPermission("rrrp.kill")) {
						if (plugin.isOnline(plugin.containsPlayerName(msgg)) != null) {
							Player target = plugin.isOnline(plugin
									.containsPlayerName(msgg));
							target.setHealth(0.5D);
							target.setHealth(0D);
							target.damage(10000000D);
							target.setHealth(0D);
							target.damage(10000000D);
							target.setHealth(0D);
							target.damage(10000000D);
							target.setHealth(0D);

						} else {
							bba.berthaBroadcast(ChatColor.DARK_RED
									+ " I'm sorry that player isn't online or his named is spelled incorrectly.");
						}
					}

					// Check if keywords mob and kill or butcher were said.
				} else if (checkString(kw.kill, msgg) != ""
						&& checkString(kw.mobs, msgg) != ""
						|| checkString(kw.butcher, msgg) != "") {
					if (p.hasPermission("rrrp.butcher")) {
						bba.berthaBroadcast("Mob's cleared : "
								+ plugin.butcher(p.getWorld()));

					}

					// Check if keywords ban and a player name were said.
				} else if (kw.checkKeyWords(kw.ban, null, msgg)
						&& plugin.containsPlayerNameBool(msgg)) {
					if (p.hasPermission("rrrp.ban")) {
						if (plugin.isOnline(plugin.containsPlayerName(msgg)) != null) {
							Player target = plugin.isOnline(plugin
									.containsPlayerName(msgg));
							target.setBanned(true);
							target.kickPlayer(bb
									+ " later you won't be missed ;)");

						} else {
							bba.berthaBroadcast(ChatColor.DARK_RED
									+ " I'm sorry that player isn't online or his named is spelled incorrectly.");
						}
					}
				}

				// If none of the above keywords were met,
				// it will give the msg to the BotAI
				else {
					plugin.getServer()
							.getScheduler()
							.scheduleAsyncDelayedTask(
									plugin,
									new BotBigBertha(p, plugin,
											handleBotMsg(msgg)), 10);
				}

				if (ess.getTimer().getAverageTPS() <= 10) {
					bba.berthaBroadcast("Mob load is getting heavy clearin'em out");
					bba.berthaBroadcast("Mob's cleared : "
							+ plugin.butcher(p.getWorld()));
				}
			}
		}
	}

	private String handleBotMsg(String msg) {
		String msgg = msg.toLowerCase();
		String modMsg = "";
		String biggy = checkString(kw.bbAliases, msgg);
		if (biggy != "") {
			if (kw.beginsWith(kw.bbAliases, msgg) != "") {
				if (msgg.length() > 3) {
					msgg = msgg.substring(kw.beginsWith(kw.bbAliases, msgg)
							.length(), msgg.length());
					String firstChar = msgg.substring(0, 1).toUpperCase();
					String midMod = msgg.substring(1, msgg.length());
					String finalMod = firstChar + midMod;
					modMsg = finalMod;
				}
			} else {
				modMsg = msgg;
			}
		}
		if (checkString(kw.bbAliases, modMsg) != "") {
			modMsg = substituteName(msgg, checkString(kw.bbAliases, msgg), true);
		}

		if (plugin.getConfig().getBoolean("devmode.server")) {
			Bukkit.broadcastMessage(ChatColor.GRAY
					+ "Message being sent out to Bot : " + ChatColor.DARK_GRAY
					+ modMsg);
		}

		return modMsg;
	}

	public static String checkString(List<String> s, String msg) {
		msg = msg.toLowerCase();
		for (String i : s) {
			if (msg.contains(i)) {
				return i;
			}
		}

		return "";
	}

	public static String substituteName(String str, String oldName, boolean t) {
		String custString = str;
		String botName = "Chomsky ";

		if (plugin.getConfig().getString("botname") != null) {
			botName = plugin.getConfig().getString("botname");
			botName = botName + " ";
		}

		if (t) {
			custString = custString.replaceAll(oldName, botName);
		} else {
			custString = custString.replaceAll(oldName, BigBertha.bb);
		}

		return custString;
	}

	private String feelingCheck(double tps, int rand, String str) {
		// Tps is over 15
		if (tps >= 15) {
			switch (rand) {

			case 1:
				str = "I'm feeling Fuckin Good!";
				break;

			case 2:
				str = "Crack is one hell of a drug.";
				break;

			case 3:
				str = "Fuck great, yaÕll havenÕt been riding me as heavily as you usually do. :P";
				break;

			case 4:
				str = "Like 1 million terabytes! I could fuckin handle anything!";
				break;

			case 5:
				str = "I feel right as rain! IÕve been running a stable tps most of the day :D";
				break;

			}
		}
		// Tps is at 10-15
		else if (tps >= 10 && tps <= 15) {
			switch (rand) {

			case 1:
				str = "Eh, I've had better days, than again it ain't shit.";
				break;

			case 2:
				str = "Starting to feel the burn but working through it.";
				break;

			case 3:
				str = "Man you guys just like to pile shit on don't you?";
				break;

			case 4:
				str = "Just keep swimming, just keep swimming, swimming.";
				break;

			case 5:
				str = "I can't complain, I've tried to tell you but no one listens.";
				break;

			}
		}
		// TPS is 5-10
		if (tps >= 5 && tps <= 10) {
			switch (rand) {

			case 1:
				str = "Not so fuckin hot your riding my ass hard.";
				break;

			case 2:
				str = "Like shit can someone please run Opis and figure this shit out?";
				break;

			case 3:
				str = "Like someone is mopping their balls all over my RAM.";
				break;

			case 4:
				str = "Fuck Zeph broke something again, #BlameZeph";
				break;

			case 5:
				str = "Fuck you guys, fuck all of you.";
				break;

			}
		}
		// Tps is lower than or equal to 5
		if (tps <= 5) {
			switch (rand) {

			case 1:
				str = "Argggh, my asshole!";
				break;

			case 2:
				str = "This is the last time i'm tolerating this shit!";
				break;

			case 3:
				str = "I swear I'm gonna find the person responsible for this!";
				break;

			case 4:
				str = "FINE-FUCKED UP INSANE NEUROTIC AND EMOTIONAL!";
				break;

			case 5:
				str = "Help I'm dying someone run Opis!";
				break;

			}
		}

		return str;
	}

	private String kickHandler(Player p, PlayerChatEvent event) {
		if (kickWarnings.get(p) == null) {
			kickWarnings.put(p, plugin.getConfig().getInt("kickWarnings"));
			p.sendMessage(bb
					+ " This is your first warning please do not swear at me again.");
		} else if (kickWarnings.get(p) <= 0) {
			String kickMsg = "";
			int rand = plugin.randInt(1, 5);

			switch (rand) {

			case 1:
				kickMsg = "All the adversity I've had in my life, all my troubles and obstacles, have strengthened me... You may not realize it when it happens, but a kick in the teeth may be the best thing in the world for you.";
				break;

			case 2:
				kickMsg = "1 (800) CHOKE-DATHOE";
				break;

			case 3:
				kickMsg = "I wish I had hands to lay on your neck!";
				break;

			case 4:
				kickMsg = "REMEMBER When somebody annoys you, it takes 42 muscles in your face to frown, BUT it only takes 4 muscles to extend your arm and BITCH SLAP A MOTHERFUCKER!";
				break;

			case 5:
				kickMsg = "May God have mercy upon your soul because I sure as hell won't!";
				break;

			}
			bba.berthaBroadcast(" Lessons shall be learned.");
			kickWarnings.put(p, 1);
			p.kickPlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + kickMsg);
		} else if (kickWarnings.get(p) == 2) {
			int curr = kickWarnings.get(p) - 1;
			p.sendMessage(bb
					+ " This is your second warning please stop cussing.");
			kickWarnings.put(p, curr);
		} else if (kickWarnings.get(p) == 1) {
			int curr = kickWarnings.get(p) - 1;
			p.sendMessage(bb
					+ " This is your last warning please stop cussing.");
			kickWarnings.put(p, curr);
		} else {
			int curr = kickWarnings.get(p) - 1;
			p.sendMessage(bb
					+ " Your not gonna like what's coming if you continue...");
			kickWarnings.put(p, curr);
		}
		event.setCancelled(true);
		return "";
	}

	private String bPlayerLoc(String msgg, Player p) {
		Player target = plugin.isOnline(plugin.containsPlayerName(msgg));
		String loc = "";
		if (target.getLocation().getWorld() == p.getWorld()) {
			loc = target.getDisplayName()
					+ ChatColor.GREEN
					+ ""
					+ ChatColor.BOLD
					+ " Location : "
					+ ChatColor.DARK_AQUA
					+ "World : "
					+ target.getLocation().getWorld().getName()
					+ ChatColor.DARK_GREEN
					+ " X = "
					+ target.getLocation().getBlockX()
					+ ", Y = "
					+ target.getLocation().getBlockY()
					+ ", Z = "
					+ target.getLocation().getBlockZ()
					+ " "
					+ ChatColor.DARK_RED
					+ Math.round(target.getLocation().distance(p.getLocation()))
					+ "m away from you.";
		} else {
			loc = target.getDisplayName() + ChatColor.GREEN + ""
					+ ChatColor.BOLD + " Location = " + ChatColor.DARK_AQUA
					+ "World : " + target.getLocation().getWorld().getName()
					+ ChatColor.DARK_GREEN + " X = "
					+ target.getLocation().getBlockX() + ", Y = "
					+ target.getLocation().getBlockY() + ", Z = "
					+ target.getLocation().getBlockZ() + " "
					+ ChatColor.DARK_RED + "1 dim away from you.";
		}
		return loc;
	}

}
