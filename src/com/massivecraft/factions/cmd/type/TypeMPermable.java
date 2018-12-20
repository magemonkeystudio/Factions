package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.command.type.TypeAbstractChoice;

import java.util.Collections;

public class TypeMPermable extends TypeAbstractChoice<MPerm.MPermable>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static TypeMPermable i = new TypeMPermable();
	public static TypeMPermable get() { return i; }
	private TypeMPermable()
	{
		super(Rank.class);

		this.faction = null;
		this.setAll(Collections.emptyList());
	}

	public static TypeMPermable get(Faction faction) { return new TypeMPermable(faction); }
	public TypeMPermable(Faction faction)
	{
		super(MPerm.MPermable.class);
		if (faction == null) throw new NullPointerException("faction");

		this.faction = faction;

		var permables = MPerm.getPermables(faction);
		
		this.setAll(permables);
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	private final Faction faction;
	public Faction getFaction() { return this.faction; }

}
