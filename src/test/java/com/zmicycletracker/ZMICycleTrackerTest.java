package com.zmicycletracker;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ZMICycleTrackerTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ZMICycleTrackerPlugin.class);
		RuneLite.main(args);
	}
}