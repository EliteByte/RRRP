package net.rerenderreality.elitebyte.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RRRPSRPerksClass implements Listener {

	/*
	 * This class is used to handle all the RankPerks
	 */

	public static RRRPMainClass plugin;

	public RRRPSRPerksClass(RRRPMainClass plugin) {
		if (plugin != null)
			RRRPSRPerksClass.plugin = plugin;
	}

	public static void woodPerk(Player p) {
		p.getInventory().addItem(woodPerkItem(p));
	}

	public void stonePerk(Player p) {
		p.getInventory().addItem(stonePerkItem(p.getName()));
	}

	public void woodPerkUse(Player p) {

		Location pLoc = p.getLocation();
		int woodH = 64;
		Location loc = pLoc;
		loc.setX(pLoc.getX());
		loc.setZ(pLoc.getZ());
		loc.setY(validBlocks(pLoc, 30));
		p.sendMessage(ChatColor.DARK_AQUA
				+ "Your Rank Perk was used and spawned @ Y-Level : "
				+ ChatColor.BOLD + "" + ChatColor.DARK_RED + loc.getBlockY()
				+ ChatColor.DARK_AQUA + " expRadius : " + ChatColor.BOLD + ""
				+ ChatColor.DARK_RED
				+ plugin.getConfig().getLong("ranks.wood.explosionRadius"));

		@SuppressWarnings("deprecation")
		ItemStack m = new ItemStack(Material.getMaterial(plugin.getConfig()
				.getInt("ranks.wood.material")));
		if (m != null && m.getType().isBlock()
				&& plugin.getConfig().getInt("ranks.wood.material") != 0) {
			fallingBlocks(woodH, m.getType(), loc, (long) 0.5);
		} else {
			fallingBlocks(woodH, Material.LOG, loc, (long) 0.5);
		}
		if (plugin.checkDev(p) == false)
			subtractPlayerHandItem(1, p);
	}

	public void woodPerkReceive(Player p, int coolAmt) {
		if (p.hasPermission("rrrp.rankperk.wood")) {
			double remainingTime = plugin.remainingCooler(p, "wood", coolAmt);
			if (remainingTime >= coolAmt || plugin.checkDev(p)) {
				plugin.commenceCooler(p, "wood", coolAmt);
				RRRPSRPerksClass.woodPerk(p);
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ "Still cooling, please wait another "
						+ ChatColor.DARK_AQUA + coolAmt(coolAmt, remainingTime)
						+ ChatColor.DARK_RED + " min:sec");
			}
		} else {
			p.sendMessage(ChatColor.DARK_RED
					+ "You don't have permission to the WoodPerk please contact Elite it this is wrong.");
		}
	}

	private void subtractPlayerHandItem(int amt, Player p) {
		ItemStack hand = p.getItemInHand();
		int amount = hand.getAmount();
		if (amount > 1) {
			hand.setAmount(amount - amt);
			p.setItemInHand(hand);
		} else {
			p.setItemInHand(new ItemStack(Material.AIR));
		}
	}

	private int validBlocks(Location loc, int h) {
		final int y = loc.getBlockY();
		int b = 0;

		for (int i = 1; i <= h; i++) {
			Location temp = loc;
			temp.setY(y + i);
			if (loc.getWorld().getBlockAt(temp).getType() != Material.AIR) {
				return y + i - 1;
			} else
				b = (int) temp.getY();
		}
		return b;
	}

	public void fallingBlocks(final int delay, final Material mat,
			final Location loc, final long delayb) {
		plugin.getServer().getScheduler()
				.scheduleSyncRepeatingTask(plugin, new Runnable() {
					int timeLeft = delay;

					@Override
					public void run() {
						if (timeLeft != -1) {
							if (timeLeft != 0) {
								if (plugin.getConfig().get(
										"ranks.wood.explosionRadius") != ""
										&& plugin.getConfig().get(
												"ranks.wood.explosionRadius") != null) {
									loc.getWorld()
											.createExplosion(
													loc.getBlockX(),
													loc.getBlockY(),
													loc.getBlockZ(),
													Float.parseFloat(plugin
															.getConfig()
															.getString(
																	"ranks.wood.explosionRadius")),
													false, false);
									spawnFallingBlock(mat, loc);
									timeLeft--;
								} else {
									loc.getWorld()
											.createExplosion(loc.getBlockX(),
													loc.getBlockY(),
													loc.getBlockZ(), 0.0F,
													false, false);
									spawnFallingBlock(mat, loc);
									timeLeft--;
								}

							} else {
								timeLeft--;
							}
						}
					}
				}, 0L, delayb);

	}

	@SuppressWarnings("deprecation")
	private void spawnFallingBlock(Material mat, Location loc) {
		loc.getWorld().spawnFallingBlock(loc, mat, (byte) 0);
	}

	private static ItemStack woodPerkItem(Player p) {
		ItemStack sapling = new ItemStack(Material.SAPLING);
		ItemMeta saplingMeta = sapling.getItemMeta();
		saplingMeta.addEnchant(Enchantment.LUCK, 69, true);
		saplingMeta.setDisplayName(ChatColor.DARK_AQUA + p.getName()
				+ "'s Wood Perk Sapling " + ChatColor.GRAY + " [Click to Use]");
		sapling.setItemMeta(saplingMeta);
		return sapling;
	}

	public void stonePerkReceive(Player p, int coolAmt) {
		if (p.hasPermission("rrrp.rankperk.stone")) {
			double remainingTime = plugin.remainingCooler(p, "stone", coolAmt);

			if (remainingTime >= coolAmt || plugin.checkDev(p)) {
				plugin.commenceCooler(p, "stone", coolAmt);
				p.getInventory().addItem(stonePerkItem(p.getName()));
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ "Still cooling, please wait another "
						+ ChatColor.DARK_AQUA + coolAmt(coolAmt, remainingTime)
						+ ChatColor.DARK_RED + " [Min : Sec]");
			}
		} else {
			p.sendMessage(ChatColor.DARK_RED
					+ "You don't have permission to the StonePerk please contact Elite it this is wrong.");
		}

	}

	private ItemStack stonePerkItem(String pName) {
		ItemStack stoneItem = new ItemStack(Material.SNOW_BALL);
		ItemMeta stoneMeta = stoneItem.getItemMeta();

		stoneMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 69, true);
		stoneMeta.setDisplayName(ChatColor.GREEN + pName + ChatColor.DARK_AQUA
				+ "'s Stone Perk Rock-Encaser " + ChatColor.GRAY
				+ " [Throw @ LivingEnties]");
		stoneItem.setItemMeta(stoneMeta);
		stoneItem.setAmount(6);

		return stoneItem;
	}

	private String coolAmt(int coolAmt, double remainingTime) {

		String timestamp = "";
		double diffTime = coolAmt - remainingTime;

		timestamp = ChatColor.BLACK + "[" + ChatColor.DARK_AQUA
				+ (int) diffTime / 60 + "M" + ChatColor.WHITE + " : "
				+ ChatColor.DARK_AQUA + (int) diffTime % 60 + "S"
				+ ChatColor.BLACK + "]";

		return timestamp;
	}

}
