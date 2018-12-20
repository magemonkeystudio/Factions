package com.massivecraft.factions.entity;

import com.massivecraft.massivecore.store.EntityInternal;

public class Rank extends EntityInternal<Rank> implements MPerm.MPermable
{
	// -------------------------------------------- //
	// OVERRIDE: ENTITY
	// -------------------------------------------- //

	@Override
	public Rank load(Rank that)
	{
		this.name = that.name;

		return this;
	}

	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	private String name;
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; this.changed(); }

	private int priority;
	public int getPriority() { return this.priority; }
	public void setPriority(int priority) { this.priority = priority; this.changed(); }

	public String getPrefix()
	{
		String ret = "";
		if (this.isLeader()) ret += "L";

		if (this.getName().equalsIgnoreCase("Leader")) ret += "**";
		else if (this.getName().equalsIgnoreCase("Officer")) ret += "*";
		else if (this.getName().equalsIgnoreCase("Member")) ret += "+";
		else if (this.getName().equalsIgnoreCase("Recruit")) ret += "-";
		else ret += "=";

		return ret;
	}

	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	// For GSON
	private Rank()
	{
		this(null,0);
	}

	public Rank(String name, int priority)
	{
		this.name = name;
		this.priority = priority;
	}

	// -------------------------------------------- //
	// RANK PRIORITY
	// -------------------------------------------- //

	public boolean isLessThan(Rank otherRank)
	{
		if (this.getContainer() != otherRank.getContainer()) throw new IllegalArgumentException(this.getId() + " : " + otherRank.getId());

		return this.getPriority() < otherRank.getPriority();
	}

	public boolean isMoreThan(Rank otherRank)
	{
		if (this.getContainer() != otherRank.getContainer()) throw new IllegalArgumentException(this.getId() + " : " + otherRank.getId());

		return this.getPriority() > otherRank.getPriority();
	}

	public boolean isAtLeast(Rank otherRank)
	{
		if (this.getContainer() != otherRank.getContainer()) throw new IllegalArgumentException(this.getId() + " : " + otherRank.getId());

		return this.getPriority() >= otherRank.getPriority();
	}

	public boolean isAtMost(Rank otherRank)
	{
		if (this.getContainer() != otherRank.getContainer()) throw new IllegalArgumentException(this.getId() + " : " + otherRank.getId());

		return this.getPriority() <= otherRank.getPriority();
	}

	public boolean isLeader()
	{
		for (Rank otherRank : this.getContainer().getAll())
		{
			if (otherRank == this) continue;

			if (otherRank.isMoreThan(this)) return false;
		}
		return true;
	}

	public Rank getRankAbove()
	{
		Rank ret = null;
		for (Rank otherRank : this.getContainer().getAll())
		{
			if (otherRank == this) continue;
			if (otherRank.isLessThan(this)) continue;
			if (ret != null && ret.isLessThan(otherRank)) continue;

			ret = otherRank;
		}
		return ret;
	}

	public Rank getRankBelow()
	{
		Rank ret = null;
		for (Rank otherRank : this.getContainer().getAll())
		{
			if (otherRank == this) continue;
			if (otherRank.isMoreThan(this)) continue;
			if (ret != null && ret.isMoreThan(otherRank)) continue;

			ret = otherRank;
		}
		return ret;
	}

	// -------------------------------------------- //
	// PERM
	// -------------------------------------------- //

	/*public boolean addPerm(MPerm mperm)
	{
		var ret = this.getPermIds().add(mperm.getId());
		if (ret) this.changed();
		return ret;
	}

	public boolean removePerm(MPerm mperm)
	{
		var ret = this.getPermIds().remove(mperm.getId());
		if (ret) this.changed();
		return ret;
	}

	public boolean hasPerm(MPerm mperm)
	{
		return this.getPermIds().contains(mperm.getId());
	}*/

}
