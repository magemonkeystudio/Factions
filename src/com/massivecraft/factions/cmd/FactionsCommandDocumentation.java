package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.req.ReqFactionHomesEnabled;

public class FactionsCommandDocumentation extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	public FactionsCommandDocumentation()
	{
		this.addRequirements(ReqFactionHomesEnabled.get());
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public int num = 1;

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public void senderFields(boolean set)
	{
		super.senderFields(set);

		num = 1;
	}

	// -------------------------------------------- //
	// MESSAGE
	// -------------------------------------------- //

	public void msgDoc(String msg, String... args)
	{
		msg = "<lime>" + this.num++ + ") <i>";
		msg(msg, args);
	}

}
