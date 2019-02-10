package com.massivecraft.factions.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.util.Txt;

public class CmdFactionsSeeChunk extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsSeeChunk()
	{
		// Aliases
		this.addAliases("sc");
		
		// Parameters
		this.addParameter(TypeBooleanOn.get(), "active", "toggle");

		// Requirements
		this.addRequirements(RequirementIsPlayer.get());
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		msg("<b>Seechunk might not work on Minecraft 1.13, perhaps try: ");
		message(CmdFactions.get().cmdFactionsSeeChunkOld.getTemplate());
		// Args
		boolean old = msender.isSeeingChunk();
		boolean targetDefault = !old;
		boolean target = this.readArg(targetDefault);
		String targetDesc = Txt.parse(target ? "<g>ON": "<b>OFF");
		
		// NoChange
		if (target == old)
		{
			msg("<i>See Chunk is already %s<i>.", targetDesc);
			return;
		}
		
		// Apply
		msender.setSeeingChunk(target);
		
		// Inform
		msg("<i>See Chunk is now %s<i>.", targetDesc);
	}

}
