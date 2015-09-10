package net.rerenderreality.elitebyte.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class RRRPMainClass extends JavaPlugin implements Listener {

	
	public final Logger logger = Logger.getLogger("Minecraft");
	public final PluginDescriptionFile pdffile = getDescription();
	public static RRRPMainClass plugin;
	public final RRRPCommandClass commandClass = new RRRPCommandClass(this);
	public final RRRPSRPerksClass perksClass = new RRRPSRPerksClass(this);
	public final BigBerthaActions bba = new BigBerthaActions();
	public final TabCompletion tabCompleter = new TabCompletion(this);
	public final BigBertha bigBertha = new BigBertha(this);
	public GroupManager groupManager;
	
	
	@Override
	public void onDisable(){
	    this.logger.info(pdffile.getName() + "Version" + pdffile.getVersion() + " Has been Diabled!");
	    saveConfig();
	    
	  }

	  @Override
	  public void onEnable() {
		this.logger.info("EliteByte's Re-RenderRealityPlugin has successfully loaded.");  
		
	    PluginManager pm = getServer().getPluginManager();
	    getCommand("rrrp").setTabCompleter(new TabCompletion(this));
	    pm.registerEvents(new RRRPCommandClass(this), this);
	    pm.registerEvents(new RRRPSRPerksClass(this), this);
	    pm.registerEvents(new BigBertha(this), this);
	    pm.registerEvents(this, this);
	    getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	  }
	  
	  public String getGroup(final Player base)
		{
		  	PluginManager pm = getServer().getPluginManager();
		  	GroupManager gmm = (GroupManager) pm.getPlugin("GroupManager");
			final AnjoPermissionsHandler handler = gmm.getWorldsHolder().getWorldPermissions(base);
			
			if (handler == null)
			{
				getLogger().info("GroupManager Handler is null : " + base.getDisplayName());
				return null;
			}
			return handler.getGroup(base.getName());
		}
	  
	  
	  @Override
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String commandSent = cmd.getName().toLowerCase();
		  
	   if (sender instanceof Player) {
	   Player p = (Player) sender;
	   
	   switch(commandSent) {
	   
	   case "deathhunt" :
		   if (p.hasPermission("rrrp.deathhunt"))
		   commandClass.deathhunt(args, p);
		   else p.sendMessage("You do not have permission to access " + args[0]);
		   break;
		   
	   case "sudoc" :
		   if (p.hasPermission("rrrp.sudoc"))
		   commandClass.sudoc(args, p);
		   else p.sendMessage("You do not have permission to access " + args[0]);
		break; 
		
	   case "iteminfo" :
		   commandClass.iteminfo(p);
		   break;
		
	   case "reloadrrrp" :
		   if (p.hasPermission("rrrp.reloadrrrp"))
		   commandClass.reloadConfig();
		   else p.sendMessage("You do not have permission to access " + args[0]);
		   break;
		 
	   case "rankperk" :
		   if (p.hasPermission("rrrp.rankperk"))
		   commandClass.rankperk(args, p);
		   else p.sendMessage("You do not have permission to access " + args[0]);
		   break;
		   
	   case "rrrp" :
		   if (p.hasPermission("rrrp.rrrp"))
		   commandClass.rrrp(args, p);
		   else p.sendMessage("You do not have permission to access " + args[0]);
		   break;
		   
	   case "devmode" :
		   if (p.hasPermission("rrrp.devmode"))
		   commandClass.devModeToggle(p);
		   else p.sendMessage("You do not have permission to access " + args[0]);
		   break;
		   
	   case "bblist" :
		   if (p.hasPermission("rrrp.biglist"))
		   commandClass.bigberthaList(p);
		   else p.sendMessage("You do not have permission to access " + args[0]);
		   break;
		   
	   case "bbb" :
		   if (p.hasPermission("rrrp.bigbertha.broadcast")) {
			   if (args[0].length() != 0) {
				   BigBerthaActions.berthaBroadcast(combineArgs(args));
			   }
			   
		   }else p.sendMessage("You do not have permission to access " + args[0]); {}
		   break;
		   
	   }
	  } else if (!(sender instanceof Player)) {
		   switch (commandSent) {
			
		   }   
	   }
	return false;
	   
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
			  s2 = s2 + " " +  i;
		  }
		  
		  return s2;
	  } 
	  
	  public boolean checkDev(Player p ) {
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
	  
	  @SuppressWarnings({ "deprecation" })
	@EventHandler(priority = EventPriority.HIGH)
	  public void onPlayerUse(PlayerInteractEvent event) {
			
		    Player p = event.getPlayer();
		    
		    if (event.getAction().equals(Action.RIGHT_CLICK_AIR ) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
		    	ItemStack iHand = p.getItemInHand();
 		    	
		    	 if (iHand.getType().getId() == 6) {
		    			if (p.getItemInHand().getItemMeta().getEnchantLevel(Enchantment.LUCK) == 69) {
		    				perksClass.woodPerkUse(p);
		    			}
		    	 }
		    			

		    	 else if (iHand == new ItemStack(Material.COMPASS)) {
		    		 	if (iHand.getItemMeta().getEnchantLevel(Enchantment.LURE) == 69) {
		    		 		updateCompass(p);
		    		 	}
		    	 }
		    	 
	    }	    	
		    
	  }
	  
	public void butcher(World w) {
		  List<Entity> es = w.getEntities();
		  List<Entity> valid = new ArrayList<Entity>();
		  
		  for (Entity e : es) {
			  if (e instanceof Monster) {
					  valid.add(e);
			  }
		  }
		  
		  for (Entity e : valid) {
			  e.remove(); 
		  }
	  }
	  
	  public void updateCompass(Player p) {
		   if (getPrey(p) != null) {
			   double metersAway = getPrey(p).getLocation().distance(p.getLocation());
			   p.setItemInHand(preyCompass(getPrey(p), metersAway));
		   }  
	  }
	  
	public ItemStack preyCompass(Player prey, double metersAway) {
			final String deathhunt = ChatColor.BLACK + "" + ChatColor.BOLD + "Death" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Hunt" + ChatColor.WHITE;
			ItemStack preyCompass = new ItemStack(Material.COMPASS);
			ItemMeta preyCompassMeta = preyCompass.getItemMeta();
			preyCompassMeta.addEnchant(Enchantment.LURE, 69, true);
			preyCompassMeta.setDisplayName( deathhunt + ChatColor.DARK_AQUA + " Prey Pointer set to " + ChatColor.WHITE + "[" +  ChatColor.GRAY + prey.getDisplayName() + ChatColor.WHITE + "] " + ChatColor.DARK_AQUA + metersAway + " Meters away");
			preyCompass.setItemMeta(preyCompassMeta);
			return preyCompass;
		}
	  
		public Player getPrey(Player p) {
			Player prey = null;
			String name;
			
			if (getConfig().get("deathhuntroster." + p.getDisplayName() + ".currentPrey") != null) {
				name = getConfig().get("deathhuntroster." + p.getDisplayName() + ".currentPrey").toString();
				Bukkit.broadcastMessage(name + " Curr Prey name");
					if (playerLookup(name) != null) {
								Bukkit.broadcastMessage("Looked up player " + name);
								prey = playerLookup(name);	
				}
			}
			return prey;
		}
		
	  
	  
	  public Player playerLookup(String pName) {
		  for ( Player p : Bukkit.getOnlinePlayers()) {
			  Bukkit.broadcastMessage("PlayerLookup " + p.getDisplayName() + " compared to " + pName);
			  if ( p.getDisplayName() == pName) {
				  Bukkit.broadcastMessage("Found the playerloopup");
				  return p;
		  } 
	  	}
		return null;
	  }

	@SuppressWarnings({ "deprecation" })
	private double currentTime() {
		double finalTime = 0;
		Date dateobj = new Date();
		finalTime =  ((dateobj.getDate() - 1) * 24 * 60) + (dateobj.getHours() * 60) + dateobj.getMinutes();
		return finalTime;
	}  
	  
	public boolean hasCooled(Player p, double time) {
		if (getConfig().get("cooldown.rankperk.woodrank." + p.getName()) != null) {
			p.sendMessage(currentTime() + " : Current Time, Your Time : " + getConfig().getDouble("cooldown.rankperk.woodrank." + p.getName()));
			if (currentTime() - getConfig().getDouble("cooldown.rankperk.woodrank." + p.getName()) >= time) {
				p.sendMessage("You have cooled");
					return true;
			} else {
					return false;
			}
	} else {
		getConfig().set("cooldown.rankperk.woodrank." + p.getName(), currentTime());
	}
	return false;	
}

	public void commenceCooler(Player p, double time) {
		 p.sendMessage("You won't be able to use this for another, " + time + " minute(s)");
	}

	public double remainingCooler(Player p, double d) {
		return currentTime() - d;
	}
	
}
