package com.rclaptracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("zmicycletracker")
public interface RCLapTrackerConfig extends Config
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
