package net.rerenderreality.elitebyte.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;


public class TabCompletion implements TabCompleter {
	
	public static RRRPMainClass plugin;

	public TabCompletion(RRRPMainClass plugin) {
		TabCompletion.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		
		if (cmd.getName().equalsIgnoreCase("rrrp")) {
			if (sender instanceof Player) {
				switch (args.length) {
				
				case 1 :
					List<String> tab = Arrays.asList("admin");
					return tab;
					
				case 2 :
					List<String> tab1 = Arrays.asList("config");
					return tab1;
					
				
				case 3 :
					if (args[1].equalsIgnoreCase("config")) {
						if (args[2] != null) {
							if (plugin.getConfig().get(args[2]) != null) {
								if (args[2].equals("") || args[2].endsWith(".")) {
									sender.sendMessage(ChatColor.GRAY + "" + getAsFullPathList(args[2], false) + 
											ChatColor.DARK_GRAY +  " : " + "Current Config Paths");
									return getAsFullPathList(args[2], false);
								} else {
									if (getAsFullPathList(args[2], false).isEmpty()) {
										sender.sendMessage(ChatColor.DARK_RED + "End Of Path, Current Path's Value = " 
									+ ChatColor.DARK_GRAY + plugin.getConfig().get(args[2]));
									return null;
									}
									else {
									sender.sendMessage(ChatColor.GRAY + "" + getAsFullPathList(args[2], true) + 
											ChatColor.DARK_GRAY +  " : " + "Current Config Paths");
									return getAsFullPathList(args[2], true);
									}
								}
							}
						}
					}
					break;
				}
			}
		}
		return null;
	}
	
	 public List<String> getAsFullPathList(final String path, boolean bool) {
	        final List<String> list=new ArrayList<String>();
	        if(plugin.getConfig().contains(path) && plugin.getConfig().isConfigurationSection(path)){
	            final Set<String> keys = plugin.getConfig().getConfigurationSection(path).getKeys(false);
	            if(keys.size()>0){
	                final Object[] key = keys.toArray();
	                for(final Object element:key){
	                	if (bool) 
	                    list.add(path+"."+(String) element);
	                    else 
	                    	list.add(path + (String) element);
	                }
	            }
	        }
	        
	        return list;
	    }

}
