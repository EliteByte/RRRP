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
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
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


@SuppressWarnings("deprecation")
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
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		   break;
		   
	   case "sudoc" :
		   if (p.hasPermission("rrrp.sudoc"))
		   commandClass.sudoc(args, p);
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		break; 
		
	   case "iteminfo" :
		   commandClass.iteminfo(p);
		   break;
		
	   case "reloadrrrp" :
		   if (p.hasPermission("rrrp.reloadrrrp"))
		   commandClass.reloadConfig();
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		   break;
		 
	   case "rankperk" :
		   if (p.hasPermission("rrrp.rankperk"))
		   commandClass.rankperk(args, p);
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		   break;
		   
	   case "rrrp" :
		   if (p.hasPermission("rrrp.rrrp"))
		   commandClass.rrrp(args, p);
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		   break;
		   
	   case "devmode" :
		   if (p.hasPermission("rrrp.devmode"))
		   commandClass.devModeToggle(p);
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		   break;
		   
	   case "bblist" :
		   if (p.hasPermission("rrrp.biglist"))
		   commandClass.bigberthaList(p);
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		   break;
		   
	   case "ebb" :
		   if (p.hasPermission("rrrp.ebb"))
		   BigBerthaActions.eliteBroadcast(combineArgs(args));
		   else p.sendMessage("You do not have permission to access " + cmd.getName());
		   break;
		   
	   case "bbb" :
		   if (p.hasPermission("rrrp.bigbertha.broadcast")) {
			   if (args[0].length() != 0) {
				   BigBerthaActions.berthaBroadcast(combineArgs(args));
			   }
			   break;
			   
			   
			   
		   }else p.sendMessage("You do not have permission to access " + args[0]); {}
		   break;
		   
	   }
	  } else if (!(sender instanceof Player)) {
		   switch (commandSent) {
			
		   case "ebb" :
			   BigBerthaActions.eliteBroadcast(combineArgs(args));
			   break;			   
			   
		   case "bbp" :
				 	if (args.length < 2){
				 		if (getOnlineAdmin() != null) {
					 		getOnlineAdmin().chat(combineArgs(args));
				 		}
				 	}
				 	break;
				 	
		   case "bbb" :
				   if (args[0].length() != 0) {
					   BigBerthaActions.berthaBroadcast(combineArgs(args));
				   } sender.sendMessage(ChatColor.DARK_RED + " You didn't provide enough args. Usage: /bbb {MSG}");
				   break;
		   }   
	   }
	return false;
	   
	  }
	  
	  public Player getOnlineAdmin() {
		  
		  for (Player p : Bukkit.getOnlinePlayers()) {
			  if (p.isOp() || p.hasPermission("rrrp.admin") ) {
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
	  
	public int butcher(World w) {
			int numberRemoved = 0;
		
			for (Chunk chunk : w.getLoadedChunks())
			{
				for (Entity e : chunk.getEntities())		
				{
					if ( e instanceof Monster || e instanceof ComplexLivingEntity || e instanceof Flying || e instanceof Slime ) {
							numberRemoved++;
							e.remove();
					}
					
				}
			}
			
			return numberRemoved;
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
			
			if (getConfig().get("deathhuntroster." + p.getName() + ".currentPrey") != null) {
				name = getConfig().get("deathhuntroster." + p.getName() + ".currentPrey").toString();
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
			  if ( p.getDisplayName() == pName) {
				  return p;
		  } 
	  	}
		return null;
	  }

	  /*
	   * This method gets the current amount of minutes
	   * we have passed since the first minute of the month
	   * so Ex:
	   * if the date = January the 2nd the time is 4:21 p.m
	   * This would look like this in my equation
	   * (2nd - 1) * 24 * 60
	   * Because it is the 2nd but it hasn't passed 48 hours
	   *  I subtract 1 from the current day
	   * And then I multiply it by 24 and then 60 because their
	   * are 24 hours in a day and 60 minutes in an hour.
	   * and it's 4:21 p.m which I convert to military time
	   * so it's 16:21 so now I do 16 * 60 and add 21
	   * Because their has been 16 *60 minutes that have passed
	   * and add the minutes of the day too
	   * So the whole equation looks like
	   * ((day - 1) * 24 * 60) + (hour2military * 60 + minute) ==
	   *  2421 minutes passed from the beggining of the month to current date.
	   */
	private double currentTime() {
		double finalTime = 0;
		Date dateobj = new Date();
		finalTime =  (((dateobj.getDate() - 1) * 24 * 60) + (dateobj.getHours() * 60) + dateobj.getMinutes()) * 60 + dateobj.getSeconds();
		//Bukkit.broadcastMessage("Current Time : " + finalTime + " MinutesForm : " +  Math.round(finalTime/60.0));
		return finalTime;
	}
	
	public void commenceCooler(Player p , String string, int coolAmt) {
			getConfig().set("ranks." + string + "." + p.getName(), currentTime());
			saveConfig();
			p.sendMessage(ChatColor.DARK_AQUA + "" + coolAmt/60 + ChatColor.GRAY + " minute(s) until you can use this perk again" );
	}
	public double remainingCooler(Player p, String string, int coolAmt) {
		if (getConfig().getDouble("ranks." + string + "." + p.getName()) != 0) {
			double curr = currentTime();
			//Bukkit.broadcastMessage(ChatColor.RED + "Config : " + getConfig().getDouble("ranks." + string + "." + p.getName()));
			//Bukkit.broadcastMessage("CurrentTime : "+ curr);
			//Bukkit.broadcastMessage(ChatColor.DARK_AQUA +  "Remaining : " + (getConfig().getDouble("ranks." + string + "." + p.getName()) - curr) * -1);
			return (getConfig().getDouble("ranks." + string + "." + p.getName()) - curr) * -1 ;
		} else {
			commenceCooler(p, string, coolAmt);
			return 0;
		}
	} 

	  
	
}
