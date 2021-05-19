package me.henry.SMPUtilities.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.henry.SMPUtilities.Main;

public class Give implements CommandExecutor{
	private Main plugin;

	public Give(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("give").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		Player recipient = Bukkit.getPlayer(args[0]);
		
		try{
			
			//String recipient = args[0];
			
			//for(Player p : playerList) {
				//if(p.getDisplayName().equals(anObject)) {
					
				//}
			//}
			
			
		} catch(ArrayIndexOutOfBoundsException ignored) {
			
		}
		
		
		return false;
	}

}
