package com.zmicycletracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("zmicycletracker")
public interface ZMICycleTrackerConfig extends Config
{
	@ConfigItem(
			keyName = "highestPouch",
			name = "Highest Pouch",
			description = "Configures the counter reset value to match the highest pouch in use",
			position = 1
	)
	default HighestPouch highestPouch()
	{
		return HighestPouch.COLOSSAL;
	}
}
