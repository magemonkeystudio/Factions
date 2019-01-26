package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.command.type.TypeAbstractChoice;
import com.massivecraft.massivecore.store.EntityInternal;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Collections;

public abstract class TypeEntityInternalFaction<E extends EntityInternal<E>> extends TypeAbstractChoice<E>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	protected TypeEntityInternalFaction(Class<E> clazz)
	{
		super(clazz);

		this.faction = null;
		this.setAll(Collections.emptyList());
	}

	protected TypeEntityInternalFaction(Class<E> clazz, Faction faction)
	{
		super(clazz);
		if (faction == null) throw new NullPointerException("faction");

		this.faction = faction;

		this.setAll(this.getAll(faction));
	}

	protected abstract Collection<E> getAll(Faction faction);
	
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

}
