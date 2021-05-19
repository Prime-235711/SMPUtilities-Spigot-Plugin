package me.henry.SMPUtilities.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.google.gson.Gson;

import me.henry.SMPUtilities.Account;
import me.henry.SMPUtilities.Main;

public class WithdrawMessy implements CommandExecutor{
	private Main plugin;

	public WithdrawMessy(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("withdrawmessy").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		World world = player.getWorld();
		
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		
		int numToWithdraw = 0;
		
		Material matToWithdraw = Material.WHEAT_SEEDS;
		
		try {
			
			args[0] = args[0].toUpperCase();
			
			matToWithdraw = Material.getMaterial("LEGACY_" + args[0]);
		} catch(ArrayIndexOutOfBoundsException ignored) {
			player.sendMessage(ChatColor.RED + "Invalid material input material amound e.g. /withdraw minecraft:diamond 64");
			return false;
		}
		
		if(matToWithdraw == null) {
			
			matToWithdraw = Material.getMaterial(args[0]);
			
			if(matToWithdraw == null) {
				player.sendMessage(ChatColor.RED + "Invalid material input material amound e.g. /withdraw minecraft:diamond 64");
				return false;
			}
		}
		
		boolean amountGiven = true;
		
		try {
			@SuppressWarnings("unused")
			String catcher = args[1];
			
			try{
				numToWithdraw = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Invalid amount input valid amound e.g. /withdraw minecraft:diamond 64");
				return false;
			}
			
		} catch(ArrayIndexOutOfBoundsException ignored) {
			amountGiven = false;
		}
		
		if(playerData.has(new NamespacedKey(plugin, "account"), PersistentDataType.STRING)) {
			Gson gson = new Gson();
			
			String accountJson = playerData.get(new NamespacedKey(plugin, "account"), PersistentDataType.STRING);
			
			Account account = gson.fromJson(accountJson, Account.class);
			
			if(!amountGiven) {
				numToWithdraw = account.getAmount(matToWithdraw);
			}

			if(account.withdraw(matToWithdraw, numToWithdraw)) {				
				world.dropItemNaturally(player.getLocation(), new ItemStack(matToWithdraw, numToWithdraw));
			} else {
				player.sendMessage(ChatColor.RED + "You do not have enough to withdraw. You only have " + String.valueOf(account.getAmount(matToWithdraw)) + " " + matToWithdraw.toString());
				return false;
			}
			
			accountJson = gson.toJson(account);
			
			playerData.set(new NamespacedKey(plugin, "account"), PersistentDataType.STRING, accountJson);
			
			player.sendMessage(ChatColor.GREEN + "Successfully withdrew " + String.valueOf(numToWithdraw) + " " + args[0]);
			
		} else {
			player.sendMessage(ChatColor.RED + "Your bank account is empty.");
		}
		
		return false;
	}
}
