package com.massivecraft.factions.integration.placeholderapi;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderFactions extends PlaceholderExpansion
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static PlaceholderFactions i = new PlaceholderFactions();
	public static PlaceholderFactions get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	public String getIdentifier()
	{
		return "factions";
	}

	public String getAuthor()
	{
		return "Madus";
	}

	public String getVersion()
	{
		return Factions.get().getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String params)
	{
		System.out.println("A");
		if (player == null) return null;
		System.out.println("B");


		MPlayer mplayer = MPlayer.get(player);
		if ("role".equals(params)) params = "rank";
		switch (params)
		{
			case "faction": return mplayer.getFaction().describeTo(mplayer);
			case "power": return Double.toString(mplayer.getPower());
			case "powermax": return Double.toString(mplayer.getPowerMax());
			case "factionpower": return Double.toString(mplayer.getFaction().getPower());
			case "factionpowermax": return Double.toString(mplayer.getFaction().getPowerMax());
			case "title": return mplayer.getTitle();
			case "rank": return mplayer.getRank().getName();
			case "claims": return Long.toString(BoardColl.get().getAll().stream().mapToInt(board -> board.getCount(mplayer.getFaction())).sum());
			case "onlinemembers": return Integer.toString(mplayer.getFaction().getMPlayersWhereOnlineTo(mplayer).size());
			case "allmembers": return Integer.toString(mplayer.getFaction().getMPlayers().size());
		}
        return null;
    }
}
