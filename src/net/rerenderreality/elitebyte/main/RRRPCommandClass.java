package net.rerenderreality.elitebyte.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.rerenderreality.elitebyte.bigbertha.BigBertha;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import pw.prok.imagine.util.Array;

public class RRRPCommandClass implements Listener {

	public static RRRPMainClass plugin;
	public RRRPSRPerksClass perksClass = new RRRPSRPerksClass(plugin);
	private final String hashtagBoard = "####################################################";
	public final String deathhunt = ChatColor.BLACK + "" + ChatColor.BOLD
			+ "Death" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Hunt"
			+ ChatColor.WHITE;

	public RRRPCommandClass(RRRPMainClass plugin) {
		RRRPCommandClass.plugin = plugin;
		perksClass = new RRRPSRPerksClass(plugin);
	}

	@SuppressWarnings("deprecation")
	public void iteminfo(Player p) {
		p.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "Item's :");
		p.sendMessage(ChatColor.BLUE + "ID : " + p.getItemInHand().getTypeId());
		p.sendMessage(ChatColor.BLUE + "Amt. : "
				+ p.getItemInHand().getAmount());
		p.sendMessage(ChatColor.BLUE + "Durability : "
				+ p.getItemInHand().getDurability());
		p.sendMessage(ChatColor.BLUE + "MaxStackSize : "
				+ p.getItemInHand().getMaxStackSize());

	}

	public void deathhunt(String args[], Player p) {

		if (args.length == 0) {
			p.sendMessage(ChatColor.AQUA
					+ p.getDisplayName()
					+ ChatColor.GRAY
					+ ""
					+ ChatColor.ITALIC
					+ ", Please use '/deathhunt help' to view all it's possible commands.");
		}

		if (args.length > 0) {

			switch (args[0]) {

			case "help":
				p.sendMessage("----------------=" + deathhunt
						+ ChatColor.ITALIC + "" + ChatColor.WHITE + " Commands"
						+ "=----------------");

				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "join");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE + "Join the roster for "
						+ deathhunt);
				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "roster");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE
						+ "View the list of people who joined the " + deathhunt
						+ " roster list");
				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "balance");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE
						+ "View how much money you have in your " + deathhunt
						+ " virtual-wallet");
				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "rank");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE
						+ "View your current ranking in " + deathhunt);
				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "prey");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE
						+ "View the current player you are hunting");
				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "roll");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE + "Roll the die of death!");
				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "kit [KitName]");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE + "Select your default kit for "
						+ deathhunt);
				p.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "kitlist");
				p.sendMessage(ChatColor.GREEN + "Usage : " + ChatColor.ITALIC
						+ "" + ChatColor.WHITE
						+ "View all kits you have available.");
				break;

			case "join":
				p.sendMessage(ChatColor.GREEN + "You have been added to the "
						+ deathhunt + ChatColor.GREEN + " roster, Good Luck!");
				deathhuntJoin(p);
				break;

			case "roster":
				p.sendMessage(ChatColor.GRAY + "----------------="
						+ ChatColor.DARK_RED + "Roster" + ChatColor.GRAY
						+ "=----------------");
				p.sendMessage(ChatColor.BLACK + hashtagBoard);
				p.sendMessage(ChatColor.BLACK + "#" + deathhuntRoster()
						+ ChatColor.BLACK + "#");
				p.sendMessage(ChatColor.BLACK + hashtagBoard);
				break;

			case "remove":
				if (args[1] != null) {
					if (p.hasPermission("rrrp.deathhunt.leave")) {
						p.sendMessage(ChatColor.RED + "Taking "
								+ ChatColor.GRAY + args[1] + ChatColor.RED
								+ " off of the roster.");
						plugin.getConfig().set(
								"deathhuntroster." + p.getDisplayName()
										+ ".isPlaying", false);
						plugin.saveConfig();
						p.performCommand("dh roster");
					} else
						p.sendMessage(ChatColor.DARK_RED
								+ "Sorry you don't have permisison to do this command. Go to Elite if this is wrong.");
				}
				break;

			case "compass":
				if (plugin.getPrey(p) != null) {
					Player prey = p;
					prey = plugin.getPrey(p);
					if (p.getLocation().getWorld() == prey.getWorld()) {
						double metersAway = p.getLocation().distance(
								prey.getLocation());
						p.getInventory().addItem(
								plugin.preyCompass(prey, metersAway, p));
						p.setCompassTarget(prey.getLocation());
					} else {
						p.getInventory().addItem(
								plugin.preyCompass(prey, -2, p));
					}
				} else {
					p.getInventory().addItem(plugin.preyCompass(null, -1, p));
				}
				break;

			case "roll":
				@SuppressWarnings("unused")
				int randNumber = plugin.randInt(0, validPlayers().size());
				break;

			case "preyset":
				if (p.hasPermission("rrrp.deathhunt.preyset")) {
					if (args[1] != null) {
						p.sendMessage(ChatColor.RED + "Prey set to "
								+ ChatColor.WHITE + args[1] + ChatColor.RED
								+ ", happy hunting ~ Elite ;)");
						plugin.getConfig().set(
								"deathhuntroster." + p.getName()
										+ ".currentPrey", args[1]);
						plugin.saveConfig();
					}
				} else {
					p.sendMessage(ChatColor.DARK_RED
							+ "Sorry you don't have permisison to do this command. Go to Elite if this is wrong.");
				}
				break;

			default:
				p.sendMessage(ChatColor.DARK_RED + "I'm sorry but, " + "'"
						+ args[0] + "'" + " is an invalid " + deathhunt
						+ " command.");
				p.sendMessage(ChatColor.AQUA
						+ p.getDisplayName()
						+ ChatColor.GRAY
						+ ""
						+ ChatColor.ITALIC
						+ ", Please use '/deathhunt help' to view all it's possible commands.");
				break;

			}

		}

	}

	public void rrrp(String[] args, CommandSender sender) {

		if (args.length == 0) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.DARK_RED + "Usage: /rrrp help");
				if (sender.isOp()
						|| plugin.getGroup((Player) sender).equalsIgnoreCase(
								"Admin")
						|| plugin.getGroup((Player) sender).equalsIgnoreCase(
								"DevGod")
						|| plugin.getGroup((Player) sender).equalsIgnoreCase(
								"Owner")
						|| plugin.getGroup((Player) sender).equalsIgnoreCase(
								"Webmaster")) {
					sender.sendMessage(ChatColor.DARK_GRAY
							+ "Usage for Admins: /rrrp admin");
				}
			}
		}

		else if (args.length > 0) {

			switch (args[0]) {

			case "admin":
				if (sender.hasPermission("rrrp.admin")) {
					if (args.length == 1) {
						sender.sendMessage(ChatColor.DARK_RED
								+ "Usage: /rrrp admin {Config, Help} {ConfigPath} {Value} {ValueType : 'String', 'int', 'double', 'float', 'long' }");
						sender.sendMessage(ChatColor.DARK_AQUA
								+ "Example: /rrrp admin config ranks.woodrank.woodPerkExplosionRadius 1.0");
					} else {
						switch (args[1].toLowerCase()) {

						case "help":
							sender.sendMessage(ChatColor.GRAY
									+ " Ummm..... I haven't made any admin commands.... #BlameSchool");
							break;

						case "config":
							if (args.length < 5) {
								sender.sendMessage(ChatColor.DARK_RED
										+ "Usage: /rrrp admin {Config, Help} {ConfigPath} {Value}"
										+ " {ValueType : 'String', 'int', 'double', 'float', 'long' }");
							} else {

								switch (args[4]) {

								case "String":
									sender.sendMessage(ChatColor.DARK_AQUA
											+ "Changing config : "
											+ ChatColor.RED + args[2]
											+ ChatColor.GRAY + " = "
											+ ChatColor.BLUE + args[3] + " "
											+ ChatColor.DARK_GRAY + "Type = "
											+ args[4]);
									plugin.getConfig().set(args[2], args[3]);
									break;

								case "int":
									sender.sendMessage(ChatColor.DARK_AQUA
											+ "Changing config : "
											+ ChatColor.RED + args[2]
											+ ChatColor.GRAY + " = "
											+ ChatColor.BLUE + args[3] + " "
											+ ChatColor.DARK_GRAY + "Type = "
											+ args[4]);
									plugin.getConfig().set(args[2],
											Integer.parseInt(args[3]));
									break;

								case "double":
									sender.sendMessage(ChatColor.DARK_AQUA
											+ "Changing config : "
											+ ChatColor.RED + args[2]
											+ ChatColor.GRAY + " = "
											+ ChatColor.BLUE + args[3] + " "
											+ ChatColor.DARK_GRAY + "Type = "
											+ args[4]);
									plugin.getConfig().set(args[2],
											Double.parseDouble(args[3]));
									break;

								case "float":
									sender.sendMessage(ChatColor.DARK_AQUA
											+ "Changing config : "
											+ ChatColor.RED + args[2]
											+ ChatColor.GRAY + " = "
											+ ChatColor.BLUE + args[3] + " "
											+ ChatColor.DARK_GRAY + "Type = "
											+ args[4]);
									plugin.getConfig().set(args[2],
											Float.parseFloat(args[3]));
									break;

								case "long":
									sender.sendMessage(ChatColor.DARK_AQUA
											+ "Changing config : "
											+ ChatColor.RED + args[2]
											+ ChatColor.GRAY + " = "
											+ ChatColor.BLUE + args[3] + " "
											+ ChatColor.DARK_GRAY + "Type = "
											+ args[4]);
									plugin.getConfig().set(args[2],
											Long.parseLong(args[3]));
									break;

								case "boolean":
									sender.sendMessage(ChatColor.DARK_AQUA
											+ "Changing config : "
											+ ChatColor.RED + args[2]
											+ ChatColor.GRAY + " = "
											+ ChatColor.BLUE + args[3] + " "
											+ ChatColor.DARK_GRAY + "Type = "
											+ args[4]);
									plugin.getConfig().set(args[2],
											Boolean.parseBoolean(args[3]));
									break;

								default:
									sender.sendMessage(ChatColor.DARK_AQUA
											+ args[4]
											+ ChatColor.DARK_RED
											+ " cannot be deemed a JavaValue Type, you f*** something up, *Pwease twai agwain!*  <3 Elite");
								}
								plugin.saveConfig();
							}
							break;

						case "":

							break;
						}
					}
				} else {
					sender.sendMessage(ChatColor.DARK_RED
							+ "Sorry you don't have permission to RRRP Admin commands.");
				}

				break;

			case "help":
				sender.sendMessage("----------------=" + ChatColor.BOLD + ""
						+ ChatColor.AQUA + "RRRP" + ChatColor.ITALIC + ""
						+ ChatColor.WHITE + " Commands" + "=----------------");

				sender.sendMessage(ChatColor.DARK_GRAY + "/deathhunt "
						+ ChatColor.GRAY + "help");
				sender.sendMessage(ChatColor.GREEN + "Usage : "
						+ ChatColor.ITALIC + "" + ChatColor.WHITE + "List of "
						+ deathhunt + " Commands");

				sender.sendMessage(ChatColor.DARK_GRAY + "/rankperk "
						+ ChatColor.GRAY + "{Optional}[RankName]");
				sender.sendMessage(ChatColor.GREEN
						+ "Usage : "
						+ ChatColor.ITALIC
						+ ""
						+ ChatColor.WHITE
						+ "Receive your rank's RankPerk, or a specific RankPerk");

				break;
			}
		}

	}

	/*
	 * Called when someone does the /rankperk or /rankperk {RankName} cmd. If
	 * they don't use the cmd with a RankName it gives the player the rankperk
	 * of their current Rank.
	 */
	public void rankperk(String args[], Player p) {

		int coolAmt = 60 * 60;

		if (plugin.getConfig().get("ranks.wood.cooldown") != null) {
			coolAmt = plugin.getConfig().getInt("ranks.wood.cooldown") * 60;
		}

		if (args.length == 0) {
			String groupName = plugin.getGroup(p);

			switch (groupName) {

			case "Wood":
				if (p.hasPermission("rrrp.rankperk.wood"))
					perksClass.woodPerkReceive(p, coolAmt);
				else
					p.sendMessage(ChatColor.DARK_RED
							+ "You don't have permission to the WoodPerk please contact Elite it this is wrong.");
				break;

			case "Stone":

				if (p.hasPermission("rrrp.rankperk.stone"))
					perksClass.stonePerkReceive(p, coolAmt);
				else
					p.sendMessage(ChatColor.DARK_RED
							+ "You don't have permission to the StonePerk please contact Elite it this is wrong.");
				break;

			case "Bronze":

				if (p.hasPermission("rrrp.rankperk.bronze"))
					perksClass.bronzePerkReceive(p, coolAmt);
				else
					p.sendMessage(ChatColor.DARK_RED
							+ "You don't have permission to the BronzePerk please contact Elite it this is wrong.");
				break;

			}
		}

		else if (args.length > 0) {

			switch (args[0].toLowerCase()) {

			case "wood":
				if (p.hasPermission("rrrp.rankperk.wood"))
					perksClass.woodPerkReceive(p, coolAmt);
				else
					p.sendMessage(ChatColor.DARK_RED
							+ "You don't have permission to the WoodPerk please contact Elite it this is wrong.");
				break;

			case "stone":
				if (p.hasPermission("rrrp.rankperk.stone"))
					perksClass.stonePerkReceive(p, coolAmt);
				else
					p.sendMessage(ChatColor.DARK_RED
							+ "You don't have permission to the StonePerk please contact Elite it this is wrong.");
				break;

			case "bronze":
				if (p.hasPermission("rrrp.rankperk.bronze"))
					perksClass.bronzePerkReceive(p, coolAmt);
				else
					p.sendMessage(ChatColor.DARK_RED
							+ "You don't have permission to the BronzePerk please contact Elite it this is wrong.");
				break;

			}
		}
	}

	/*
	 * Called when someone does /deathhunt join Put's the player in the roster
	 * for deathhunt
	 */
	@SuppressWarnings("unchecked")
	private void deathhuntJoin(Player p) {
		String pName = p.getName();

		if (plugin.getConfig().get("deathhuntroster." + pName) == null
				|| plugin.getConfig().getBoolean(
						"deaththuntroster." + pName + ".isPlaying") == false) {
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
			plugin.getConfig().set("deathhuntroster." + pName + ".DOJ",
					df.format(dateobj));
			plugin.getConfig().set("deathhuntroster." + pName + ".isPlaying",
					true);
			List<String> roster = Array.asList();
			if (plugin.getConfig().getList("deathhuntroster.roster") != null) {
				roster = (List<String>) plugin.getConfig().getList(
						"deathhuntroster.roster");
			}
			roster.add(p.getName());
			plugin.getConfig().set("deathhuntroster.roster", roster);
			plugin.saveConfig();

		} else
			p.sendMessage(ChatColor.DARK_RED
					+ "It seems you are already on the roster, if this is wrong contact a Dev.");
	}

	/*
	 * This returns a String of all the players that are in the validPlayers()
	 * list and combines all the player names into one String with a space
	 * inbetween each.
	 */
	private String deathhuntRoster() {
		String stringList = "";
		Set<String> validPlayers = validPlayers();
		for (String currentP : validPlayers) {
			stringList = stringList + ChatColor.DARK_RED + currentP + " ";
		}
		return stringList;
	}

	/*
	 * This returns a List of Strings of the people in the config who have
	 * deathhuntroster.{PlayerName}.isPlaying set to true
	 */
	private Set<String> validPlayers() {
		String[] validPlayers = new String[] {};
		Set<String> validPlayers2 = new HashSet<String>(
				Array.asList(validPlayers));
		Set<String> roster = plugin.getConfig()
				.getConfigurationSection("deathhuntroster.").getKeys(false);

		for (String currentP : roster) {
			if (plugin.getConfig().getBoolean(
					"deathhuntroster." + currentP + ".isPlaying")) {
				validPlayers2.add(currentP);
			}
		}
		return validPlayers2;
	}

	@SuppressWarnings("deprecation")
	// This method is my version of /sudo cmd
	public void sudoc(String[] args, CommandSender sender) {
		Player target = null;

		if (args.length > 1) {
			if (args[0] != null) {
				if (Bukkit.getPlayer(args[0]) != null) {
					target = Bukkit.getPlayer(args[0]);
					if (target.getName() != "EliteByte"
							&& plugin.getConfig().getList("sudoBlacklist")
									.contains(target.getName())) {
						if (args.length == 2) {

							if (target.isOp())
								target.performCommand(args[1]);
							else {
								target.setOp(true);
								target.performCommand(args[1]);
								target.setOp(false);
							}
						} else {
							if (target.isOp())
								target.performCommand(performMultiCommand(args,
										target.getName()));
							else {
								target.setOp(true);
								target.performCommand(performMultiCommand(args,
										target.getName()));
								target.setOp(false);
							}
						}
					}
				} else {
					sender.sendMessage(ChatColor.DARK_RED + args[0]
							+ " Is not online :/");

				}
			}
		}
	}

	private String performMultiCommand(String[] args, String target) {
		String joinedString = StringUtils.join(args, " ");
		joinedString = joinedString.substring(target.length() + 1);
		return joinedString;
	}

	public void reloadConfig() {
		Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD
				+ "RRRP Config Reloaded!");
		plugin.reloadConfig();
	}

	public boolean devModeCheck(Player p) {
		return plugin.getConfig().getBoolean("devmode." + p.getName());
	}

	public void devModeToggle(Player p) {
		String pName = p.getName();

		if (plugin.getConfig().contains("devmode." + pName)) {
			if (plugin.getConfig().getBoolean("devmode." + pName) == true) {
				plugin.getConfig().set("devmode." + pName, false);
				plugin.saveConfig();
			} else {
				plugin.getConfig().set("devmode." + pName, true);
				plugin.saveConfig();
			}

		} else {
			plugin.getConfig().set("devmode." + pName, true);
			plugin.saveConfig();
		}
		p.sendMessage(ChatColor.AQUA + "Set Developer Mode to "
				+ ChatColor.BOLD + "" + ChatColor.DARK_GRAY
				+ plugin.getConfig().getBoolean("devmode." + pName));
	}

	public void bigberthaList(Player p) {
		p.sendMessage("Current " + BigBertha.bb + " Features");
		p.sendMessage(plugin.getConfig().getList("bigbertha.list") + "");
	}

}
