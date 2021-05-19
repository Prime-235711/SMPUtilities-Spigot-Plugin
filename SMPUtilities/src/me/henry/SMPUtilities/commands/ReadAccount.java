package me.henry.SMPUtilities.commands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.google.gson.Gson;

import me.henry.SMPUtilities.Account;
import me.henry.SMPUtilities.Main;

public class ReadAccount implements CommandExecutor{
	private Main plugin;

	public ReadAccount(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("account").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		
		if(playerData.has(new NamespacedKey(plugin, "account"), PersistentDataType.STRING)) {
			
			Gson gson =new Gson();
			
			String accountJson = playerData.get(new NamespacedKey(plugin, "account"), PersistentDataType.STRING);
			
			Account account = gson.fromJson(accountJson, Account.class);
			
			account.getAccount(player);
			
			accountJson = gson.toJson(account);
			
			playerData.set(new NamespacedKey(plugin, "account"), PersistentDataType.STRING, accountJson);
			
		} else {
			player.sendMessage(ChatColor.GOLD + "Your bank account is empty.");
		}
		
		return false;
	}
	
}
