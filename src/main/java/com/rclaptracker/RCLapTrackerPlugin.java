package com.rclaptracker;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.Counter;
import net.runelite.client.config.ConfigManager;

import java.awt.image.BufferedImage;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

import net.runelite.api.events.AnimationChanged;


@Slf4j
@PluginDescriptor(
	name = "ZMI Cycle Tracker"
)
public class RCLapTrackerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private InfoBoxManager infoBoxManager;
	@Inject
	private RCLapTrackerConfig config;
	@Inject
	private ItemManager itemManager;

	@Provides
	RCLapTrackerConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RCLapTrackerConfig.class);
	}

	private Counter counterBox = null;

	private int target;
	private int cycle;
	private boolean hasCrafted = false;
	private boolean isMidRun = false;

	private static final int SPELL_CONTACT_ANIMATION_ID = 4413;
	private static final int CRAFT_RUNES_ANIMATION_ID = 791;

	@Override
	protected void startUp(){
		target = config.highestPouch().getTarget();
		cycle = target;
		updateInfoBox();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged ev)
	{
		target = config.highestPouch().getTarget();
		if (!isMidRun) {
			cycle = target;
			updateInfoBox();
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		infoBoxManager.removeInfoBox(counterBox);
		counterBox = null;
	}

	private void removeInfobox()
	{
		if (counterBox != null) {
			infoBoxManager.removeInfoBox(counterBox);
		}
		counterBox = null;
	}

	private void updateInfoBox()
	{
		removeInfobox();
		final BufferedImage image = itemManager.getImage(ItemID.ELDER_CHAOS_HOOD, 1, false);
		counterBox = new Counter(image, this, cycle);
		infoBoxManager.addInfoBox(counterBox);
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		if (client.getLocalPlayer() == null || client.getLocalPlayer().getName() == null)
		{
			return;
		}

		String playerName = client.getLocalPlayer().getName();
		String actorName = event.getActor().getName();

		if (!playerName.equals(actorName)) {
			return;
		}

		int animId = event.getActor().getAnimation();
		if (animId == SPELL_CONTACT_ANIMATION_ID) {
			cycle = target;
			isMidRun = false;
			updateInfoBox();
		}
		else if (!hasCrafted && animId == CRAFT_RUNES_ANIMATION_ID) {
			hasCrafted = true;
			isMidRun = true;
			if (cycle > 0) {
				cycle--;
			}
			updateInfoBox();
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		int containerId = event.getContainerId();

		if (containerId == InventoryID.BANK.getId())
		{
			hasCrafted = false;
		}
	}
}
