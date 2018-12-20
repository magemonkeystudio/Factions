package com.massivecraft.factions.entity.migrator;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;

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
		var role = entity.remove("role").getAsString();
		var factionId = entity.get("factionId").getAsString();
		var faction = Faction.get(factionId);

		var ranks = faction.getRanks().getAll();
		for (var rank : ranks)
		{
			if (!rank.getName().equalsIgnoreCase(role)) continue;

			entity.add("rankId", new JsonPrimitive(rank.getId()));
			break;
		}
	}
	
}
