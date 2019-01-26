package com.massivecraft.factions;

import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.permissions.Permissible;

public enum Perm implements Identified
{
	// -------------------------------------------- //
	// ENUM
	// -------------------------------------------- //
	
	ACCESS,
	ACCESS_VIEW,
	ACCESS_PLAYER,
	ACCESS_FACTION,
	OVERRIDE,
	BASECOMMAND,
	CLAIM,
	CLAIM_ONE,
	CLAIM_AUTO,
	CLAIM_FILL,
	CLAIM_SQUARE,
	CLAIM_CIRCLE,
	CLAIM_ALL,
	CREATE,
	DESCRIPTION,
	DISBAND,
	DOCUMENTATION,
	DOCUMENTATION_FLAGS,
	DOCUMENTATION_PERMS,
	DOCUMENTATION_POWER,
	DOCUMENTATION_RANKS,
	EXPANSIONS,
	FACTION,
	FLAG,
	FLAG_LIST,
	FLAG_SET,
	FLAG_SHOW,
	HOME,
	INVITE,
	INVITE_LIST,
	INVITE_LIST_OTHER,
	INVITE_ADD,
	INVITE_REMOVE,
	JOIN,
	JOIN_OTHERS,
	KICK,
	LEAVE,
	LIST,
	MAP,
	MONEY,
	MONEY_BALANCE,
	MONEY_BALANCE_ANY,
	MONEY_DEPOSIT,
	MONEY_F2F,
	MONEY_F2P,
	MONEY_P2F,
	MONEY_WITHDRAW,
	MOTD,
	OPEN,
	PERM,
	PERM_LIST,
	PERM_SET,
	PERM_SHOW,
	PERM_VIEW,
	PERM_VIEWALL,
	PLAYER,
	POWERBOOST,
	POWERBOOST_PLAYER,
	POWERBOOST_FACTION,
	POWERBOOST_SET,
	RANK,
	RANK_SHOW,
	RANK_SET,
	RANK_LIST,
	RANK_EDIT,
	RANK_EDIT_CREATE,
	RANK_EDIT_NAME,
	RANK_EDIT_PREFIX,
	RANK_EDIT_PRIORITY,
	RANK_EDIT_DELETE,
	RELATION,
	RELATION_SET,
	RELATION_LIST,
	RELATION_WISHES,
	SEECHUNK,
	SEECHUNKOLD,
	SETHOME,
	SETPOWER,
	STATUS,
	NAME,
	TITLE,
	TITLE_COLOR,
	TERRITORYTITLES,
	UNCLAIM,
	UNCLAIM_ONE,
	UNCLAIM_AUTO,
	UNCLAIM_FILL,
	UNCLAIM_SQUARE,
	UNCLAIM_CIRCLE,
	UNCLAIM_ALL,
	UNSETHOME,
	UNSTUCK,
	WARP,
	WARP_GO,
	WARP_LIST,
	WARP_ADD,
	WARP_REMOVE,
	CONFIG,
	CLEAN,
	VERSION,
	
	// END OF LIST
	;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final String id;
	@Override public String getId() { return this.id; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	Perm()
	{
		this.id = PermissionUtil.createPermissionId(Factions.get(), this);
	}
	
	// -------------------------------------------- //
	// HAS
	// -------------------------------------------- //
	
	public boolean has(Permissible permissible, boolean verboose)
	{
		return PermissionUtil.hasPermission(permissible, this, verboose);
	}
	
	public boolean has(Permissible permissible)
	{
		return PermissionUtil.hasPermission(permissible, this);
	}
	
}
