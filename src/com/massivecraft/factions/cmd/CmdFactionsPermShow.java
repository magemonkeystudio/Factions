package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPerm;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.util.Txt;

import java.util.stream.Collectors;

public class CmdFactionsPermShow extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsPermShow()
	{
		// Parameters
		this.addParameter(TypeMPerm.get(), "perm");
		this.addParameter(TypeFaction.get(), "faction", "you");
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		// Arg: Faction
		MPerm mperm = this.readArg();
		Faction faction = this.readArg(msenderFaction);

		var permittedIds = faction.getPerms().get(mperm.getId());
		var permables = new MassiveList<MPerm.MPermable>();

		for (var permitted : permittedIds)
		{
			permables.add(idToMPermable(permitted));
		}

		var removeString = Txt.parse(" of ") + faction.getDisplayName(msender);
		var permableList = permables.stream()
				.map(permable -> permable.getDisplayName(msender))
				.map(s -> s.replace(removeString, ""))
				.collect(Collectors.toList());
		String permableNames = Txt.implodeCommaAnd(permableList, Txt.parse("<i>"));

		// Create messages
		msg("<i>In <reset>%s <i>permission <reset>%s <i>is granted to <reset>%s<i>.", faction.describeTo(msender), mperm.getDesc(true, false), permableNames);
	}

	public static MPerm.MPermable idToMPermable(String id)
	{
		MPlayer mplayer = MPlayerColl.get().get(id, false);
		if (mplayer != null) return mplayer;

		Faction faction = Faction.get(id);
		if (faction != null) return faction;

		for (var f : FactionColl.get().getAll())
		{
			Rank rank = f.getRank(id);
			if (rank != null) return rank;
		}

		if (Rel.ALLY.name().equalsIgnoreCase(id)) return Rel.ALLY;
		if (Rel.TRUCE.name().equalsIgnoreCase(id)) return Rel.TRUCE;
		if (Rel.NEUTRAL.name().equalsIgnoreCase(id)) return Rel.NEUTRAL;
		if (Rel.ENEMY.name().equalsIgnoreCase(id)) return Rel.ENEMY;

		throw new RuntimeException(id);
	}
	
}
