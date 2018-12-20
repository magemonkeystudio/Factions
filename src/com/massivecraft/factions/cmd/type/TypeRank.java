package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.command.type.TypeAbstractChoice;

import java.util.Collections;

public class TypeRank extends TypeAbstractChoice<Rank>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static TypeRank i = new TypeRank();
	public static TypeRank get() { return i; }
	private TypeRank()
	{
		super(Rank.class);

		this.faction = null;
		this.setAll(Collections.emptyList());
	}

	public static TypeRank get(Faction faction) { return new TypeRank(faction); }
	public TypeRank(Faction faction)
	{
		super(Rel.class);
		if (faction == null) throw new NullPointerException("faction");

		this.faction = faction;
		
		this.setAll(faction.getRanks().getAll());
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	private final Faction faction;
	public Faction getFaction() { return this.faction; }

}
