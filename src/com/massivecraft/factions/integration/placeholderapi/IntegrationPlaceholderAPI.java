package com.massivecraft.factions.integration.placeholderapi;

import com.massivecraft.massivecore.Integration;

public class IntegrationPlaceholderAPI extends Integration
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static IntegrationPlaceholderAPI i = new IntegrationPlaceholderAPI();
	public static IntegrationPlaceholderAPI get() { return i; }
	private IntegrationPlaceholderAPI()
	{
		this.setPluginName("PlaceholderAPI");
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public void setIntegrationActiveInner(boolean active)
	{
		PlaceholderFactions.get().register();
	}
	
}
