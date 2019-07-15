package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.task.TaskTax;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;

import java.util.LinkedHashMap;

public class CmdFactionsTaxInfo extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	public CmdFactionsTaxInfo()
	{
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		if (!TaskTax.get().areTaxesEnabled())
		{
			throw new MassiveException().addMsg("<b>Tax is not enabled on this server.");
		}

		LinkedHashMap<TimeUnit, Long> timeUnitcounts = TimeDiffUtil.limit(TimeDiffUtil.unitcounts(MConf.get().taxTaskPeriodMillis, TimeUnit.getAll()), 3);
		String periodString = TimeDiffUtil.formatedVerboose(timeUnitcounts);

		msg("<key>Taxation Period: <i>every %s<i>.", periodString);

		long nextTaxationTime = MConf.get().taxTaskPeriodMillis + MConf.get().taxTaskPeriodMillis;

		msg("<key>Next Taxation: %s", Txt.getTimeDeltaDescriptionRelNow(nextTaxationTime));

		String minTax = Money.format(MConf.get().taxPlayerMinimum);
		String maxTax = Money.format(MConf.get().taxPlayerMaximum);
		msg("<i>Taxes for players can be set between <reset>%s <i>and <reset>%s<i>.", minTax, maxTax);
	}
	
}
