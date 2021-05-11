package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import java.util.HashMap;

public class EnginePlayerDamage extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static EnginePlayerDamage i = new EnginePlayerDamage();
	public static EnginePlayerDamage get() { return i; }
        public HashMap<Integer, Long> coolDownTp = new HashMap<Integer, Long>();

	// -------------------------------------------- //
	// MANAGE PLAYER DAMAGE / IMMORTAL FLAG
	// -------------------------------------------- //

    //	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamaged(EntityDamageEvent event)
	{
	        if (!(event.getEntity() instanceof Player))
		{
		        return;
		}

		// If a player receive some damage...
		Player player = (Player)event.getEntity();

		//PS playerPs = PS.valueOf(player.getLocation());
		Faction psFaction = BoardColl.get().getFactionAt(PS.valueOf(player.getLocation()));

		// Bukkit.getServer().getLogger().warning("Damage on Player : " + player.getName() + ": [" + psFaction.getName() + "]");

		// Are we Immortal ?
		if (psFaction.getFlag(MFlag.getFlagImmortal()) == true)
		{
		    // Bukkit.getServer().getLogger().warning("Damage on Player " + player.getName() + ": " + event.getCause() + "=> Immortal !");
		    // Bukkit.getServer().getLogger().warning("Loc: " + player.getLocation());

		    event.setCancelled(true);

		    if ( event.getCause() == EntityDamageEvent.DamageCause.VOID)
		    {
			// TP CoolDown?
			if ( (coolDownTp.get(player.getEntityId()) == null) || ( ( System.nanoTime() - coolDownTp.get(player.getEntityId())) > 500000000))
			{
			    if( coolDownTp.get(player.getEntityId()) != null)
			    {
				coolDownTp.remove(player.getEntityId());
			    }
			    
			    coolDownTp.put(player.getEntityId(), System.nanoTime());
					  
			    // Bukkit.getServer().getLogger().warning("Respawning to: " + player.getWorld().getSpawnLocation() + " @" + System.nanoTime() + "!");
			    player.teleport(player.getWorld().getSpawnLocation());
			}
			else
			{
			    // Bukkit.getServer().getLogger().warning("Respawning Cooldown :" + System.nanoTime() + "!");
			}
		    }
		}
		else
		{
		    // Bukkit.getServer().getLogger().warning("Damage on Player " + player.getName() + ": " + event.getCause() + "=> Mortal !");
		}
	}
}
