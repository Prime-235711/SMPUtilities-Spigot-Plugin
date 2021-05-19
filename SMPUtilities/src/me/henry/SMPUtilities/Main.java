package me.henry.SMPUtilities;

import org.bukkit.plugin.java.JavaPlugin;
import me.henry.SMPUtilities.commands.Deposit;
import me.henry.SMPUtilities.commands.DepositAll;
import me.henry.SMPUtilities.commands.ReadAccount;
import me.henry.SMPUtilities.commands.Withdraw;
import me.henry.SMPUtilities.commands.WithdrawAll;
import me.henry.SMPUtilities.commands.WithdrawMessy;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		  new Deposit(this);
		  new Withdraw(this);
		  new WithdrawMessy(this);
		  new ReadAccount(this);
		  new DepositAll(this);
		  new WithdrawAll(this);
}
	
}
