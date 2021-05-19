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

public class DepositAll implements CommandExecutor{
	private Main plugin;
	
	public DepositAll(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("depositall").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;
		PlayerInventory playerInventory = player.getInventory();
				
		ItemStack[] inventory = playerInventory.getContents();
		
		for(int i = 0; i < inventory.length; i++) {
			
			ItemStack items = inventory[i];
			
			if(items != null) {
				
				Material matToDeposit = items.getType();
				if(items.hasItemMeta()) {
					player.sendMessage(ChatColor.RED + "You can not deposit your " + String.valueOf(items.getAmount()) + " " + items.getType().toString() + " because it has metadata.");
				}else {
				
					PersistentDataContainer playerData = player.getPersistentDataContainer();
					
					if(items.getAmount() > 0) {
						Gson gson = new Gson();
						
						if(playerData.has(new NamespacedKey(plugin, "account"), PersistentDataType.STRING)) {
							
							String accountJson = playerData.get(new NamespacedKey(plugin, "account"), PersistentDataType.STRING);
							
							Account account = gson.fromJson(accountJson, Account.class);
							
							account.deposit(items.getType(), items.getAmount());
							
							accountJson = gson.toJson(account);
							
							playerData.set(new NamespacedKey(plugin, "account"), PersistentDataType.STRING, accountJson);
							
							player.sendMessage(ChatColor.GREEN + "Successfully deposited " + String.valueOf(items.getAmount()) + " " + items.getType().toString());

							
							inventory[i] = new ItemStack(Material.PAPER, 0);
							
						} else {
							
							Account account = new Account(player.getDisplayName());
							
							account.deposit(items.getType(), items.getAmount());
							
							String accountJson = gson.toJson(account);
							
							playerData.set(new NamespacedKey(plugin, "account"), PersistentDataType.STRING, accountJson);
							
							player.sendMessage(ChatColor.GREEN + "Successfully deposited " + String.valueOf(items.getAmount()) + " " + items.getType().toString());
							
							inventory[i] = new ItemStack(Material.PAPER, 0);
							
						}
					}
				
				}
				
			}

		}
		
		player.getInventory().setContents(inventory);
		
		return false;
	}
	
}
	
