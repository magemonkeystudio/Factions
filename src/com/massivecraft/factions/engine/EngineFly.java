package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerUpdate;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class EngineFly extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static EngineFly i = new EngineFly();
	public static EngineFly get() { return i; }

	// -------------------------------------------- //
	// LISTENER
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMassiveCorePlayerUpdate(EventMassiveCorePlayerUpdate event)
	{
		// If we are updating a player ...
		Player player = event.getPlayer();
		if (MUtil.isntPlayer(player)) return;
		
		// ... and that player isn't in creative or spectator mode ...
		if (EventMassiveCorePlayerUpdate.isFlyAllowed(player, false)) return;
		
		// ... and the player is alive ...
		if (player.isDead()) return;

		MPlayer mplayer = MPlayer.get(player);

		// ... and the player enables flying ...
		if (!mplayer.isFlying()) return;

		// ... and the player enables flying ...
		if (!canFlyInTerritory(mplayer, PS.valueOf(player))) return;

		// ... set allowed ...
		event.setAllowed(true);
		
		// ... set speed ...
		event.setFlySpeed(MConf.get().flySpeed);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void moveChunkDetect(PlayerMoveEvent event)
	{
		// If the player is moving from one chunk to another ...
		if (MUtil.isSameChunk(event)) return;
		Player player = event.getPlayer();
		if (MUtil.isntPlayer(player)) return;

		// ... gather info on the player and the move ...
		MPlayer mplayer = MPlayer.get(player);
		PS chunkTo = PS.valueOf(event.getTo()).getChunk(true);

		// ... and they are currently flying ...
		if (!mplayer.isFlying()) return;

		// ... but can't fly at the new place ...
		if (canFlyInTerritory(mplayer, chunkTo)) return;

		// ... then perhaps they should not be
		mplayer.setFlying(false);
		deactivateForPlayer(player);
	}

	public static boolean canFlyInTerritory(MPlayer mplayer, PS ps)
	{
		Faction faction = mplayer.getFaction();
		Faction locationFaction = BoardColl.get().getFactionAt(ps.getChunk(true));

		if (faction != locationFaction) return false;
		if (!faction.getFlag(MFlag.getFlagFly())) return false;

		return true;
	}

	public static void deactivateForPlayer(Player player)
	{
		EventMassiveCorePlayerUpdate.resetFlyAllowed(player);
		EventMassiveCorePlayerUpdate.resetFlyActive(player);
		EventMassiveCorePlayerUpdate.resetFlySpeed(player);

		EventMassiveCorePlayerUpdate.run(player);
	}

}
