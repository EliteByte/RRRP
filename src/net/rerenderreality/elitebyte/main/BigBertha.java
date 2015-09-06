package net.rerenderreality.elitebyte.main;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.PluginManager;

import pw.prok.imagine.util.Array;

import com.earth2me.essentials.Essentials;

@SuppressWarnings("deprecation")
public class BigBertha implements Listener {

	public static String bb = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "" + ChatColor.GOLD + "Big" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "Bertha" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE;
	List<String> bbAliases = Array.asList("big bertha", "bigbertha", "bertha", "biggy", "biggie", "bb", "berty");
	List<String> tps = Array.asList("tps", "ticks per second");
	List<String> feeling = Array.asList("how", "feeling");
	
	private RRRPMainClass plugin;
	private Map<String, String> vars;
	private BigBerthaActions bba = new BigBerthaActions();
	
	public BigBertha(RRRPMainClass plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void chatEvent(final PlayerChatEvent event) throws Exception {
		
	    String msg = event.getMessage();
	    Player p = event.getPlayer();
	    String msgg = msg.toLowerCase();
	    
	    PluginManager pm = plugin.getServer().getPluginManager();
	  	Essentials ess = (Essentials) pm.getPlugin("Essentials");
	  	
	  	if (checkString(bbAliases, msgg)) {
	  	
	  
	  		if (checkString(tps, msgg)) {
	  			double tps = ess.getTimer().getAverageTPS();
	  			bba.berthaBroadcast("The Server TPS is : " + Math.round(tps));
	  		}
	    
	  		else if (checkString(feeling, msgg )) {
	  				int rand = plugin.randInt(1, 5);
  					String str = "";
	  				
  					bba.berthaBroadcast(feelingCheck(ess.getTimer().getAverageTPS(), rand, str));
	  		} 
	    
	  		else {
	  			vars.put("input", msg);
	  		    String response = Utils.post("http://www.pandorabots.com/pandora/talk-xml", vars);
	  		    bba.berthaBroadcast(Utils.xPathSearch(response, "//result/that/text()"));
	  		}
	    
	    }
	  	if (ess.getTimer().getAverageTPS() <= 10) {
				bba.berthaBroadcast("You boys are spawning to many mobs so bye, bye all mobs >;]");
				plugin.butcher(p.getWorld());
			}
	}
	
	
	
	private boolean checkString(List<String> s, String msg) {
		for ( String i : s) {
			if (msg.contains(i)) {
				return true;
			}
		}
		
		return false;
	}
	
	 private String feelingCheck(double tps , int rand, String str) {
		//Tps is over 15
		if ( tps >= 15 ) {
				switch (rand) {
			
				case 1 :
					str = "I'm feeling Fuckin Good!";
					break;
				
				case 2 :
					str = "Crack is one hell of a drug.";
					break;
				
				case 3 :
					str = "Fuck great, yaÕll havenÕt been riding me as heavily as you usually do. :P";
					break;
				
				case 4 :
					str = "Like 1 million terabytes! I could fuckin handle anything!";
					break;
				
				case 5 :
					str = "I feel right as rain! IÕve been running a stable tps most of the day :D";
					break;
			
			}
	}
		// Tps is at 10-15
		else if ( tps >= 10 && tps <= 15 ) {
			switch (rand) {
		
			case 1 :
				str = "Eh, I've had better days, than again it ain't shit.";
				break;
			
			case 2 :
				str = "Starting to feel the burn but working through it.";
				break;
			
			case 3 :
				str = "Man you guys just like to pile shit on don't you?";
				break;
			
			case 4 :
				str = "Just keep swimming, just keep swimming, swimming.";
				break;
			
			case 5 :
				str = "I can't complain, I've tried to tell you but no one listens.";
				break;
		
		}
}
		// TPS is 5-10
		if ( tps >= 5 && tps <= 10) {
			switch (rand) {
		
			case 1 :
				str = "Not so fuckin hot your riding my ass hard.";
				break;
			
			case 2 :
				str = "Like shit can someone please run Opis and figure this shit out?";
				break;
			
			case 3 :
				str = "Like someone is mopping their balls all over my RAM.";
				break;
			
			case 4 :
				str = "Fuck Zeph broke something again, #BlameZeph";
				break;
			
			case 5 :
				str = "Fuck you guys, fuck all of you.";
				break;
		
		}
}	
		//Tps is lower than or equal to 5
		if ( tps <= 5 ) {
			switch (rand) {
		
			case 1 :
				str = "Argggh, my asshole!";
				break;
			
			case 2 :
				str = "This is the last time i'm tolerating this shit!";
				break;
			
			case 3 :
				str = "I swear I'm gonna find the person responsible for this!";
				break;
			
			case 4 :
				str = "FINE-FUCKED UP INSANE NEUROTIC AND EMOTIONAL!";
				break;
			
			case 5 :
				str = "Help I'm dying someone run Opis!";
				break;
		
			}
		}
		
		return str;
	}
	
}

class BigBerthaActions {
	
	public void berthaBroadcast(String msg) {
		Bukkit.broadcastMessage(BigBertha.bb + " " + msg);
	}
	
}
