package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.util.MUtil;

import java.util.Collection;
import java.util.Set;

public class TypeRank extends TypeEntityInternalFaction<Rank>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static TypeRank i = new TypeRank();
	public static TypeRank get() { return i; }
	private TypeRank()
	{
		super(Rank.class);
	}

	public static TypeRank get(Faction faction) { return new TypeRank(faction); }
	public TypeRank(Faction faction)
	{
		super(Rank.class, faction);
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Collection<Rank> getAll(Faction faction)
	{
		return faction.getRanks().getAll();
	}

	@Override
	public Set<String> getNamesInner(Rank value)
	{
		return MUtil.set(value.getName(), value.getPrefix() + value.getName());
	}

}
