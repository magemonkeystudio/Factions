package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.command.type.TypeAbstractChoice;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.Set;

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

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public boolean isValid(String arg, CommandSender sender)
	{
		// In the generic case accept all
		if (this.getAll().isEmpty()) return true;
		else return super.isValid(arg, sender);
	}

	@Override
	public Set<String> getNamesInner(Rank value)
	{
		return MUtil.set(value.getName(), value.getPrefix() + value.getName());
	}

}
