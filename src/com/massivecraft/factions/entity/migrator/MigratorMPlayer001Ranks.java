package com.massivecraft.factions.entity.migrator;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;

import java.util.Collection;

public class MigratorMPlayer001Ranks extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static MigratorMPlayer001Ranks i = new MigratorMPlayer001Ranks();
	public static MigratorMPlayer001Ranks get() { return i; }
	private MigratorMPlayer001Ranks()
	{
		super(MPlayer.class);
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public void migrateInner(JsonObject entity)
	{
		String role = entity.remove("role").getAsString();
		String factionId = entity.get("factionId").getAsString();
		Faction faction = Faction.get(factionId);

		Collection<Rank> ranks = faction.getRanks().getAll();
		for (Rank rank : ranks)
		{
			if (!rank.getName().equalsIgnoreCase(role)) continue;

			entity.add("rankId", new JsonPrimitive(rank.getId()));
			break;
		}
	}
	
}
