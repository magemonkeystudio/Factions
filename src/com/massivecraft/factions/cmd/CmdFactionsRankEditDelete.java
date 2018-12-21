package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeRank;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.Txt;

import java.util.stream.Collectors;

public class CmdFactionsRankEditDelete extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	public CmdFactionsRankEditDelete()
	{
		// Parameters
		this.addParameter(TypeString.get(), "rank");
		this.addParameter(TypeFaction.get(), "faction", "you");
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		Faction faction = this.readArgAt(1, msenderFaction);

		// Rank if any passed.
		TypeRank typeRank = new TypeRank(faction);
		Rank rank = typeRank.read(this.argAt(0), sender);

		CmdFactionsRankEdit.ensureAllowed(msender, faction);

		var ranks = faction.getRanks().getAll();
		if (ranks.size() <= 2)
		{
			throw new MassiveException().addMsg("<b>A faction must have at least two ranks.");
		}

		var mplayers = faction.getMPlayersWhereRank(rank);
		if (!mplayers.isEmpty())
		{
			var count = mplayers.size();
			var names = mplayers.stream().map(m -> m.getDisplayName(sender)).collect(Collectors.toList());
			var namesDesc = Txt.implodeCommaAnd(names, Txt.parse("<i>"));
			throw new MassiveException().addMsg("<b>This rank is held by <h>%s <b>change their ranks first.", namesDesc);
		}

		String visual = rank.getVisual();
		faction.getRanks().detachEntity(rank);

		// Inform
		msg("<i>You deleted the rank <reset>%s<i>.", visual);
	}
	
}
