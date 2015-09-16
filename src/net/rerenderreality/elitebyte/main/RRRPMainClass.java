package net.rerenderreality.elitebyte.main;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import net.rerenderreality.elitebyte.bigbertha.BigBertha;
import net.rerenderreality.elitebyte.bigbertha.BigBerthaActions;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class RRRPMainClass extends JavaPlugin implements Listener {

	public final Logger logger = Logger.getLogger("Minecraft");
	public final PluginDescriptionFile pdffile = getDescription();
	public static RRRPMainClass plugin;
	public final RRRPCommandClass commandClass = new RRRPCommandClass(this);
	public final RRRPSRPerksClass perksClass = new RRRPSRPerksClass(this);
	public final BigBerthaActions bba = new BigBerthaActions();
	public final SnowballEncase sbe = new SnowballEncase(this);
	public final TabCompletion tabCompleter = new TabCompletion(this);
	public final BigBertha bigBertha = new BigBertha(this);
	public GroupManager groupManager;

	@Override
	public void onDisable() {
		this.logger.info(pdffile.getName() + "Version" + pdffile.getVersion()
				+ " Has been Diabled!");
		saveConfig();

	}

	@Override
	public void onEnable() {
		this.logger
				.info("EliteByte's Re-RenderRealityPlugin has successfully loaded.");
		plugin = this;
		PluginManager pm = getServer().getPluginManager();
		getCommand("rrrp").setTabCompleter(new TabCompletion(this));
		pm.registerEvents(new RRRPCommandClass(this), this);
		pm.registerEvents(new SnowballEncase(this), this);
		pm.registerEvents(new RRRPSRPerksClass(this), this);
		pm.registerEvents(new BigBertha(this), this);
		pm.registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

	}

	public String getGroup(final Player base) {
		PluginManager pm = getServer().getPluginManager();
		GroupManager gmm = (GroupManager) pm.getPlugin("GroupManager");
		final AnjoPermissionsHandler handler = gmm.getWorldsHolder()
				.getWorldPermissions(base);

		if (handler == null) {
			getLogger().info(
					"GroupManager Handler is null : " + base.getDisplayName());
			return null;
		}
		return handler.getGroup(base.getName());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		String commandSent = cmd.getName().toLowerCase();

		if (sender instanceof Player) {
			Player p = (Player) sender;

			switch (commandSent) {

			case "deathhunt":
				if (p.hasPermission("rrrp.deathhunt"))
					commandClass.deathhunt(args, p);
				else
					p.sendMessage("You do not have permission to access "
							+ cmd.getName());
				break;

			case "sudoc":
				if (p.hasPermission("rrrp.sudoc"))
					commandClass.sudoc(args, p);
				else
					p.sendMessage("You do not have permission to access "
							+ cmd.getName());
				break;

			case "iteminfo":
				commandClass.iteminfo(p);
				break;

			case "reloadrrrp":
				if (p.hasPermission("rrrp.reloadrrrp"))
					commandClass.reloadConfig();
				else
					p.sendMessage("You do not have permission to access "
							+ cmd.getName());
				break;

			case "rankperk":
				commandClass.rankperk(args, p);
				break;

			case "rrrp":
				if (p.hasPermission("rrrp.rrrp"))
					commandClass.rrrp(args, p);
				else
					p.sendMessage("You do not have permission to access "
							+ cmd.getName());
				break;

			case "devmode":
				if (p.hasPermission("rrrp.devmode"))
					commandClass.devModeToggle(p);
				else
					p.sendMessage("You do not have permission to access "
							+ cmd.getName());
				break;

			case "bblist":
				if (p.hasPermission("rrrp.biglist"))
					commandClass.bigberthaList(p);
				else
					p.sendMessage("You do not have permission to access "
							+ cmd.getName());
				break;

			case "ebb":
				if (p.getName() == "EliteByte")
					BigBerthaActions.eliteBroadcast(combineArgs(args));
				else
					p.sendMessage("Only Elite can send EliteMessages, just stop.");
				break;

			case "bbb":
				if (p.hasPermission("rrrp.bigbertha.broadcast")) {
					if (args[0].length() != 0) {
						bba.berthaBroadcast(combineArgs(args));
					}
				} else
					p.sendMessage("You do not have permission to access "
							+ cmd.getName());
				break;
			case "rankperkall":
				if (p.hasPermission("rrrp.rankperk.all")) {
					if (args.length != 0) {
						rankperkAll(args[0], args, sender);
					} else {
						p.sendMessage(ChatColor.DARK_RED + "Not enough args.");
					}
				}
				break;

			case "eggnade":
				if (p.hasPermission("eggnade.receive")) {
					ItemStack egg = new ItemStack(Material.EGG);
					egg.setAmount(16);
					ItemMeta eggMeta = egg.getItemMeta();
					eggMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD
							+ "Egg" + ChatColor.DARK_GREEN + ""
							+ ChatColor.BOLD + "Nade");
					eggMeta.addEnchant(Enchantment.ARROW_INFINITE, 69, true);
					egg.setItemMeta(eggMeta);
					p.getInventory().addItem(egg);
				}
				break;

			}
		} else if (sender instanceof ConsoleCommandSender) {
			switch (commandSent) {

			case "ebb":
				BigBerthaActions.eliteBroadcast(combineArgs(args));
				break;

			case "bbp":
				if (args.length < 2) {
					if (getOnlineAdmin() != null) {
						getOnlineAdmin().chat(combineArgs(args));
					}
				}
				break;

			case "bbb":
				if (args[0].length() != 0) {
					bba.berthaBroadcast(combineArgs(args));
				} else
					sender.sendMessage(ChatColor.DARK_RED
							+ " You didn't provide enough args. Usage: /bbb {MSG}");
				break;

			case "rrrp":
				commandClass.rrrp(args, sender);
				break;

			case "sudoc":
				commandClass.sudoc(args, sender);
				break;

			case "rankperkall":
				if (args.length != 0) {
					rankperkAll(args[0], args, sender);
				} else {
					sender.sendMessage("Not enough args. Usage: /rpa {RankName} {CustomMessage}-Optional");
				}
				break;
			}
		}
		return false;

	}

	private void rankperkAll(String string, String[] args, CommandSender sender) {

		for (Player p : Bukkit.getOnlinePlayers()) {
			switch (string.toLowerCase()) {

			case "wood":
				RRRPSRPerksClass.woodPerk(p);
				break;

			case "stone":
				perksClass.stonePerk(p);
				break;
			}
			sender.sendMessage(ChatColor.DARK_RED + "I'm sorry but "
					+ ChatColor.RED + string + ChatColor.DARK_RED
					+ " hasn't been implemented");
		}
		if (args.length == 1) {
			bba.berthaBroadcast("Here's a " + string + "Perk on me, Enjoy!");
		} else {
			args[0] = "";
			bba.berthaBroadcast(combineArgs(args));
		}

	}

	public Player getOnlineAdmin() {

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isOp() || p.hasPermission("rrrp.admin")) {
				return p;
			}
		}

		return null;
	}

	public boolean containsPlayerNameBool(String msg) {

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (msg.contains(p.getName().toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public String containsPlayerName(String msg) {

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (msg.contains(p.getName().toLowerCase())) {
				return p.getName();
			}
		}
		return "";
	}

	public Player isOnline(String name) {

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}

	private String combineArgs(String[] s) {
		String s2 = "";
		for (String i : s) {
			s2 = s2 + " " + i;
		}

		return s2;
	}

	public boolean checkDev(Player p) {
		if (getConfig().getBoolean("devmode." + p.getName()) == true)
			return true;
		else
			return false;
	}

	public int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public int butcher(World w) {
		int numberRemoved = 0;

		for (Chunk chunk : w.getLoadedChunks()) {
			for (Entity e : chunk.getEntities()) {
				if (e instanceof Monster || e instanceof ComplexLivingEntity
						|| e instanceof Flying || e instanceof Slime) {
					numberRemoved++;
					e.remove();
				}

			}
		}

		return numberRemoved;
	}

	public void updateCompass(Player p) {
		if (getPrey(p) != null) {
			if (getPrey(p).getWorld() == p.getWorld()) {
				double metersAway = getPrey(p).getLocation().distance(
						p.getLocation());
				p.setItemInHand(preyCompass(getPrey(p), metersAway, p));
				p.setCompassTarget(getPrey(p).getLocation());

			} else {
				p.setItemInHand(preyCompass(getPrey(p), -2, p));
			}
		}
	}

	public ItemStack preyCompass(Player prey, double metersAway, Player p) {
		final String deathhunt = ChatColor.BLACK + "" + ChatColor.BOLD
				+ "Death" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Hunt"
				+ ChatColor.WHITE;
		ItemStack preyCompass = new ItemStack(Material.COMPASS);
		ItemMeta preyCompassMeta = preyCompass.getItemMeta();
		preyCompassMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		if (metersAway > 0) {
			preyCompassMeta.setDisplayName(deathhunt + ChatColor.DARK_AQUA
					+ " Prey Pointer set to " + ChatColor.WHITE + "["
					+ ChatColor.GRAY + prey.getDisplayName() + ChatColor.WHITE
					+ "] " + ChatColor.DARK_AQUA + Math.round(metersAway)
					+ " Meters away");
		} else if (metersAway == -2) {
			preyCompassMeta.setDisplayName(deathhunt + ChatColor.DARK_AQUA
					+ " Prey Pointer set to " + ChatColor.WHITE + "["
					+ ChatColor.GRAY + prey.getLocation().getWorld().getName()
					+ ChatColor.WHITE + "]");
		} else {
			preyCompassMeta.setDisplayName(deathhunt + ChatColor.DARK_AQUA
					+ " Prey Pointer set to " + ChatColor.WHITE + "["
					+ ChatColor.GRAY + getPreyName(p) + ChatColor.WHITE + "]");
		}
		preyCompass.setItemMeta(preyCompassMeta);
		return preyCompass;
	}

	public Player getPrey(Player p) {
		Player prey = null;
		String name;

		if (getPreyName(p) != "") {
			name = getPreyName(p);
			if (isOnline(name) != null) {
				prey = isOnline(name);
			}
		}
		return prey;
	}

	public String getPreyName(Player p) {
		String name = "";

		if (getConfig().get("deathhuntroster." + p.getName() + ".currentPrey") != null) {
			name = getConfig().get(
					"deathhuntroster." + p.getName() + ".currentPrey")
					.toString();
		}
		return name;
	}

	/*
	 * This method gets the current amount of minutes we have passed since the
	 * first minute of the month so Ex: if the date = January the 2nd the time
	 * is 4:21 p.m This would look like this in my equation (2nd - 1) * 24 * 60
	 * Because it is the 2nd but it hasn't passed 48 hours I subtract 1 from the
	 * current day And then I multiply it by 24 and then 60 because their are 24
	 * hours in a day and 60 minutes in an hour. and it's 4:21 p.m which I
	 * convert to military time so it's 16:21 so now I do 16 * 60 and add 21
	 * Because their has been 16 *60 minutes that have passed and add the
	 * minutes of the day too So the whole equation looks like ((day - 1) * 24 *
	 * 60) + (hour2military * 60 + minute) == 2421 minutes passed from the
	 * beggining of the month to current date.
	 */
	private double currentTime() {
		double finalTime = 0;
		Date dateobj = new Date();
		finalTime = (((dateobj.getDate() - 1) * 24 * 60)
				+ (dateobj.getHours() * 60) + dateobj.getMinutes())
				* 60 + dateobj.getSeconds();
		// Bukkit.broadcastMessage("Current Time : " + finalTime +
		// " MinutesForm : " + Math.round(finalTime/60.0));
		return finalTime;
	}

	public void commenceCooler(Player p, String string, int coolAmt) {
		getConfig().set("ranks." + string + "." + p.getName(), currentTime());
		saveConfig();
		p.sendMessage(ChatColor.DARK_AQUA + "" + coolAmt / 60 + ChatColor.GRAY
				+ " minute(s) until you can use this perk again");
	}

	public double remainingCooler(Player p, String string, int coolAmt) {
		if (getConfig().getDouble("ranks." + string + "." + p.getName()) != 0) {
			double curr = currentTime();
			return (getConfig()
					.getDouble("ranks." + string + "." + p.getName()) - curr)
					* -1;
		} else {
			commenceCooler(p, string, coolAmt);
			return 0;
		}
	}

	/*
	 * Just for the lols EggSlopsions
	 */

	@EventHandler
	public void onPlayerThrowEgg(PlayerEggThrowEvent event) {
		Location eggLoc = event.getEgg().getLocation();
		Player thrower = event.getPlayer();

		if (thrower.getItemInHand() != null) {
			if (thrower
					.getItemInHand()
					.getItemMeta()
					.getDisplayName()
					.contains(
							ChatColor.GOLD + "" + ChatColor.BOLD + "Egg"
									+ ChatColor.DARK_GREEN + ""
									+ ChatColor.BOLD + "Nade")) {
				if (getConfig().getString("eggnade.explosionRadius") == "")
					thrower.getLocation().getWorld()
							.createExplosion(eggLoc, 0F);
				else
					thrower.getLocation()
							.getWorld()
							.createExplosion(
									eggLoc,
									Float.parseFloat(getConfig().getString(
											"eggnade.explosionRadius")));
			}
		}
	}

	@SuppressWarnings("unused")
	private Snowball customSnowball(Player p) {
		Snowball sb = p.throwSnowball();
		sb.setTicksLived(6969);
		Vector v = sb.getVelocity();
		v.multiply(4);
		sb.setVelocity(v);
		return sb;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event) {

		Player p = event.getPlayer();

		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)
				|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack iHand = p.getItemInHand();

			switch (iHand.getTypeId()) {

			case 6:
				if (iHand.getEnchantmentLevel(Enchantment.LUCK) == 69) {
					perksClass.woodPerkUse(p);
				}
				break;

			case 345:
				if (iHand.getItemMeta().getDisplayName()
						.contains(commandClass.deathhunt)) {
					updateCompass(p);
				}
				break;

			case 332:
				if (iHand
						.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
					Event e = new ProjectileLaunchEvent(p);
					getServer().getPluginManager().callEvent(e);

				}

			}

		}

	}
}
