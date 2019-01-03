package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.type.TypeAbstract;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TypeMPermable extends TypeAbstract<MPerm.MPermable>
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
	}

	public static TypeMPermable get(Faction faction) { return new TypeMPermable(faction); }
	public TypeMPermable(Faction faction)
	{
		super(MPerm.MPermable.class);
		if (faction == null) throw new NullPointerException("faction");

		this.faction = faction;
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
	public MPerm.MPermable read(String arg, CommandSender sender) throws MassiveException
	{
		TypeRank typeRank = new TypeRank(this.getFaction());
		try
		{
			Rank rank = typeRank.read(arg, sender);
			return rank;
		}
		catch (MassiveException ex)
		{
			// Do nothing
		}

		try
		{
			Rel rel = TypeRelation.get().read(arg, sender);
			return rel;
		}
		catch (MassiveException ex)
		{
			// Do nothing
		}

		try
		{
			MPlayer mplayer = TypeMPlayer.get().read(arg, sender);
			return mplayer;
		}
		catch (MassiveException ex)
		{
			// Do nothing
		}

		try
		{
			Faction faction = TypeFaction.get().read(arg, sender);
			return faction;
		}
		catch (MassiveException ex)
		{
			// Do nothing
		}

		if (arg.contains("-"))
		{
			int idx = arg.indexOf('-');
			String factionName = arg.substring(0, idx);
			String rankName = arg.substring(idx+1);

			Faction faction = TypeFaction.get().read(factionName, sender);
			Rank rank = TypeRank.get(faction).read(rankName, sender);
			return rank;
		}

		throw new MassiveException().addMsg("<b>No rank, relation, player or faction matches: <h>%s<b>.", arg);
	}

	public Collection<String> getTabList(CommandSender sender, String arg)
	{
		List<String> ret = new MassiveList<>();
		Faction faction = this.getFaction();
		if (faction == null) faction = MPlayer.get(sender).getFaction();
		ret.addAll(faction.getRanks().getAll().stream().map(Rank::getName).collect(Collectors.toList()));
		ret.addAll(TypeRelation.get().getTabList(sender, arg));
		ret.addAll(TypeFaction.get().getTabList(sender, arg));

		// Faction specific ranks
		if (arg.length() >= 2)
		{
			for (Faction f : FactionColl.get().getAll())
			{
				String name = f.getName();
				if (arg.length() <= name.length() && !name.toLowerCase().startsWith(arg.toLowerCase())) continue;
				if (arg.length() > name.length() && !arg.toLowerCase().startsWith(name.toLowerCase())) continue;

				ret.addAll(f.getRanks().getAll().stream().map(r -> name + "-" + r.getName()).collect(Collectors.toList()));
			}
		}

		return ret;
	}


	@Override
	public boolean isValid(String arg, CommandSender sender)
	{
		// In the generic case accept all
		if (this.getFaction() == null) return true;
		else return super.isValid(arg, sender);
	}

}
