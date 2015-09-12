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
	
	
	
	public RRRPMainClass plugin;
	
	public RRRPSRPerksClass (RRRPMainClass plugin) {
		this.plugin = plugin;
}
	
	public static void woodPerk(Player p) {
		p.getInventory().addItem(woodPerkItem(p));
	}
	
	public void woodPerkUse(Player p) {
			
				Location pLoc = p.getLocation();
				int woodH = 64;
				Location loc = pLoc;
				loc.setX(pLoc.getX());
				loc.setZ(pLoc.getZ());
				loc.setY(validBlocks(pLoc, 30));
				p.sendMessage(ChatColor.DARK_AQUA + "Your Rank Perk was used and spawned @ Y-Level : " + ChatColor.BOLD + "" + ChatColor.DARK_RED + loc.getBlockY()
					+ ChatColor.DARK_AQUA + " expRadius : " + ChatColor.BOLD + "" + ChatColor.DARK_RED + plugin.getConfig().getLong("ranks.woodrank.rankPerkExplosionRadius"));	
				
				@SuppressWarnings("deprecation")
				ItemStack m = new ItemStack(Material.getMaterial(plugin.getConfig().getInt("ranks.woodrank.rankPerkMaterial")));
				if (m != null && m.getType().isBlock()) {
					fallingBlocks(woodH, m.getType(), loc, (long) 0.5);
					
				}
				else {
					fallingBlocks(woodH, Material.LOG, loc, (long) 0.5);
				}
				if (plugin.checkDev(p) == false)
				subtractPlayerHandItem(1, p);
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
		  }	else
			  b = (int) temp.getY();
	  }
	  return b;
}

public void fallingBlocks(final int delay, final Material mat, final Location loc, final long delayb) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int timeLeft = delay;
			
				public void run() {
					if (timeLeft != -1) {
						if (timeLeft != 0) {
							if ( plugin.getConfig().getFloatList("ranks.woodrank.rankPerkExplosionRadius") != null) {
								loc.getWorld().createExplosion(loc, Float.parseFloat(plugin.getConfig().getString("ranks.woodrank.rankPerkExplosionRadius")), false);
								spawnFallingBlock(mat, loc);
								timeLeft--;
							} else {
								loc.getWorld().createExplosion(loc, 0.0F, false);
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
	
	private static ItemStack woodPerkItem (Player p) {
		ItemStack sapling = new ItemStack(Material.SAPLING);
		ItemMeta saplingMeta= sapling.getItemMeta();
		saplingMeta.addEnchant(Enchantment.LUCK, 69, true);
		saplingMeta.setDisplayName( ChatColor.DARK_AQUA + p.getName() + "'s Wood Perk Sapling " + ChatColor.GRAY + " [Click to Use]");
		sapling.setItemMeta(saplingMeta);
		return sapling;
	}
	
	
	
	
}
