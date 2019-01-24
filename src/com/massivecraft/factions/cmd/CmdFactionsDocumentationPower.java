package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.MassiveException;

public class CmdFactionsDocumentationPower extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	public CmdFactionsDocumentationPower()
	{

	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		msg("<lime>1) <i>All players  have an amount of power ranging from <h>%.2f <i>to <h>%.2f<i>.", MConf.get().powerMin, MConf.get().powerMax);
		msg("<lime>2) <i>The power of a faction is equal to the combined power of all it's members.");
		msg("<lime>3) <i>Your power is <h>%.2f<i>", msender.getPower());
		msg("<lime>4) <i>Your faction's power is <h>%.2f<i>", msenderFaction.getPower());
		msg("<lime>5) <i>The amount of chunks a faction can claim is the amount power it has.");
		msg("<lime>6) <i>For every hour you are online you gain <h>%.2f <i>power.", MConf.get().powerPerHour);
		msg("<lime>7) <i>Every time you die you power is decreased by <h>%.2f <i>.", MConf.get().powerPerDeath*-1);
		if (!MConf.get().canLeaveWithNegativePower && MConf.get().powerMin < 0)
		{
			msg("8) <i>You can't leave a faction if your power is negative.");
		}

	}
	
}
