package net.rerenderreality.elitebyte.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

public class SnowballEncase implements Listener {

	public static RRRPMainClass plugin;
	public Set<UUID> snowballs = new HashSet<UUID>();

	public SnowballEncase(RRRPMainClass plugin) {
		if (plugin != null)
			SnowballEncase.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSnowballHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Snowball) {
			Snowball sb = (Snowball) event.getEntity();
			if ((!snowballs.isEmpty()) && snowballs.contains(sb.getUniqueId())) {
				if (sb.getShooter() instanceof Player) {
					Player shooter = (Player) sb.getShooter();
					List<Entity> nearbyEntities = sb.getNearbyEntities(1, 1, 1);
					for (int i = 0; i < nearbyEntities.size(); i++) {
						if (nearbyEntities.get(i) instanceof LivingEntity) {
							if (nearbyEntities.get(i) instanceof Player
									&& plugin.getConfig().getBoolean(
											"ranks.stone.players") == false) {
								return;
							} else {
								LivingEntity living = (LivingEntity) nearbyEntities
										.get(i);
								if (shooter != nearbyEntities.get(i)) {
									int pMat = plugin.getConfig().getInt(
											"ranks.stone.material");
									Material encasmentMaterial = Material.STONE;
									if (plugin.getConfig().getInt(
											"ranks.stone.material") != 0) {
										encasmentMaterial = Material
												.getMaterial(pMat);
									}
									incaseLivingEntity(living,
											encasmentMaterial);

									return;
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		if (e.getEntityType() == EntityType.SNOWBALL) {
			if (e.getEntity().getShooter() instanceof Player) {
				Player p = (Player) e.getEntity().getShooter();
				if (p.getItemInHand().containsEnchantment(
						Enchantment.PROTECTION_ENVIRONMENTAL)) {
					snowballs.add(e.getEntity().getUniqueId());
				}
			}
		}
	}

	private void incaseLivingEntity(LivingEntity possibleTarget, Material mat) {
		Location entLoc = possibleTarget.getLocation();
		World entWorld = entLoc.getWorld();
		int x = entLoc.getBlockX();
		int y = entLoc.getBlockY();
		int z = entLoc.getBlockZ();

		for (int i = 0; i <= 2; i++) {
			Material mat2;
			if (i == 1) {
				mat2 = Material.GLASS;
			} else {
				mat2 = mat;
			}
			replaceAir(x - 1, y + i, z, entWorld, mat2);
			replaceAir(x + 1, y + i, z, entWorld, mat);

			replaceAir(x, y + i, z - 1, entWorld, mat2);
			replaceAir(x, y + i, z + 1, entWorld, mat2);

			replaceAir(x - 1, y + i, z - 1, entWorld, mat2);
			replaceAir(x + 1, y + i, z + 1, entWorld, mat2);

			replaceAir(x - 1, y + i, z + 1, entWorld, mat2);
			replaceAir(x + 1, y + i, z - 1, entWorld, mat2);
		}
		if (plugin.getConfig().getBoolean("ranks.stone.explosive") == true) {
			int i = entLoc.getBlockY() + 5;
			Location entLoc2 = entLoc;
			entLoc2.setY(i);
			for (int t = 1; t <= 24; t++) {
				customTNT(entWorld.spawn(entLoc2, TNTPrimed.class));
			}
			entWorld.createExplosion(x, y, z, 1.0F, false, false);
		}
		if (plugin.getConfig().getBoolean("ranks.stone.shocking") == true) {
			entWorld.strikeLightning(entLoc);
		}

	}

	private TNTPrimed customTNT(TNTPrimed tnt) {
		Vector v = tnt.getVelocity();
		v.multiply(10);
		tnt.setIsIncendiary(false);
		tnt.setVelocity(v);
		tnt.setYield(0F);
		return tnt;
	}

	private void replaceAir(int x, int y, int z, World w, Material mat) {
		if (w.getBlockAt(x, y, z).getType() == Material.AIR) {
			Location loc = new Location(w, x, y, z);
			w.playSound(loc, Sound.STEP_STONE, 1.0F, 1.0F);
			w.getBlockAt(x, y, z).setType(mat);
		}
	}

}
