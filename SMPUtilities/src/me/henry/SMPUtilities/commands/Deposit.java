package me.henry.SMPUtilities.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.google.gson.Gson;

import me.henry.SMPUtilities.Account;
import me.henry.SMPUtilities.Main;

public class Deposit implements CommandExecutor{
	private Main plugin;
	
	public Deposit(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("deposit").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		PlayerInventory playerInventory = player.getInventory();
		ItemStack itemsToDeposit = playerInventory.getItemInMainHand();
		
		Material matToDeposit = itemsToDeposit.getType();
		if(itemsToDeposit.hasItemMeta()) {
			player.sendMessage(ChatColor.RED + "You can not deposit your " + String.valueOf(itemsToDeposit.getAmount()) + " " + itemsToDeposit.getType().toString() + " because it has metadata.");
			return false;
		}
		
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		
		if(itemsToDeposit.getAmount() > 0) {
			Gson gson = new Gson();
			
			if(playerData.has(new NamespacedKey(plugin, "account"), PersistentDataType.STRING)) {
				
				String accountJson = playerData.get(new NamespacedKey(plugin, "account"), PersistentDataType.STRING);
				
				Account account = gson.fromJson(accountJson, Account.class);
				
				account.deposit(itemsToDeposit.getType(), itemsToDeposit.getAmount());
				
				accountJson = gson.toJson(account);
				
				playerData.set(new NamespacedKey(plugin, "account"), PersistentDataType.STRING, accountJson);
				
				player.sendMessage(ChatColor.GREEN + "Successfully deposited " + String.valueOf(itemsToDeposit.getAmount()) + " " + itemsToDeposit.getType().toString());
				
				playerInventory.setItemInMainHand(new ItemStack(Material.PAPER, 0));
				
			} else {
				
				Account account = new Account(player.getDisplayName());
				
				account.deposit(itemsToDeposit.getType(), itemsToDeposit.getAmount());
				
				String accountJson = gson.toJson(account);
				
				playerData.set(new NamespacedKey(plugin, "account"), PersistentDataType.STRING, accountJson);
				
				player.sendMessage(ChatColor.GREEN + "Successfully deposited " + String.valueOf(itemsToDeposit.getAmount()) + " " + itemsToDeposit.getType().toString());
				
				playerInventory.setItemInMainHand(new ItemStack(Material.PAPER, 0));
				
			}
		} else {
			
			player.sendMessage(ChatColor.RED + "You need to have an item in your hand to deposit");
			
		}
				
		return false;
		
	}
}
