package me.henry.SMPUtilities;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Account {
	public String owner;
	public ArrayList<Material> type = new ArrayList<>();
	public ArrayList<Integer> amount = new ArrayList<>();
	
	public Account(String owner) {
		this.owner = owner;

	}
	
	public boolean deposit(Material mat,  int amountToDeposit) {
		
		for(int i = 0; i < type.size(); i ++) {
			if(mat.equals(type.get(i))) {
				
				amount.set(i, amount.get(i) + amountToDeposit);
				
				return true;
			}
		}
		
		type.add(mat);
		amount.add(amountToDeposit);
			
		return true;
		
	}
	
	public boolean withdraw(Material mat,  int amountToWithdraw) {
		
		for(int i = 0; i < type.size(); i ++) {
			if(mat.equals(type.get(i))) {
				
				if(amount.get(i) - amountToWithdraw >= 0) {
					amount.set(i, amount.get(i) - amountToWithdraw);
				
					return true;
				} else {
					return false;
				}
				
			}
		}
			
		return false;
		
	}
	
	public int getAmount(Material mat) {
		for(int i = 0; i < type.size(); i ++) {
			if(mat.equals(type.get(i))) {
				
				return amount.get(i);
				
			}
		}
		
		return 0;
	}
	
	public void getAccount(Player player) {
		player.sendMessage(ChatColor.GOLD + "Your account has: ");
		
		int count = 1;
		
		for(int i = 0; i < type.size(); i ++) {
			
			if(amount.get(i) > 0) {
				player.sendMessage(ChatColor.GOLD + String.valueOf(count) + ". " + type.get(i).toString() + ": " + String.valueOf(amount.get(i)));
				
				count++;
			} else {
				type.remove(i);
				amount.remove(i);
				i--;
			}
			
		}
		
	}
	
	public void withdrawAll(Player player) {
		
		World world = player.getWorld();
		
		player.sendMessage(ChatColor.GOLD + "You just withdrew: ");
		
		int count = 1;
		
		for(int i = 0; i < type.size(); i ++) {
			
			if(amount.get(i) > 0) {
				player.sendMessage(ChatColor.GOLD + String.valueOf(count) + ". " + type.get(i).toString() + ": " + String.valueOf(amount.get(i)));
								
				world.dropItemNaturally(player.getLocation(), new ItemStack(type.get(i), amount.get(i)));
				
				type.remove(i);
				amount.remove(i);
				i--;
				
				count++;
			} else {
				type.remove(i);
				amount.remove(i);
				i--;
			}
			
		}
		
	}
	
	public void wipeAccount() {
		type.clear();
		amount.clear();
	}
	
}
