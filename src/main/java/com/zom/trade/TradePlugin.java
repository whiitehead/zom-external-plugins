package com.zom.trade;

import java.text.NumberFormat;
import java.util.Locale;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.events.ClientTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Active Prices in Trade",
	description = "Uses wiki Active Prices on trade screen",
	tags = {"trade", "wiki", "price"}
)
public class TradePlugin extends Plugin
{
	@Inject
	private ItemManager itemManager;

	@Inject
	private Client client;

	// store prices in between trade screens so colors can be accurate on confirmation screen
	long myItemPrice = 0;
	long theirItemsPrice = 0;

	final String RED_COLOR = "ff0000";
	final String WHITE_COLOR = "ffffff";

	final int BALANCED_FAVORED_TRADE = 0xf89818;
	final int IMBALANCED_TRADE = 0xf80000;

	@Override
	protected void startUp() throws Exception
	{
		myItemPrice = 0;
		theirItemsPrice = 0;
	}

	@Override
	protected void shutDown() throws Exception
	{
		myItemPrice = 0;
		theirItemsPrice = 0;
	}

	@Subscribe
	public void onClientTick(ClientTick clientTick)
	{
		try
		{
			// 3 is left trade screen (yours) | 4 is right trade screen (theirs)
			if (client.getWidget(WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID, 3) != null && client.getWidget(WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID, 4) != null)
			{
				firstTradeScreen();
			}

			// 334 is the second trade screen, 4 is the child for it.
			if (client.getWidget(334, 4) != null)
			{
				secondTradeScreen();
			}
		} catch (Exception e) {
			// ignored to not spam logs
		}
	}

	// do first trade screen logic
	public void firstTradeScreen() throws Exception
	{
		myItemPrice = 0;
		theirItemsPrice = 0;

		Widget myItemContainer = client.getWidget(WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID, 25);
		Widget theirItemContainer = client.getWidget(WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID, 28);

		if (myItemContainer == null || theirItemContainer == null)
		{
			return;
		}

		Widget[] myItemContainerChildren = myItemContainer.getDynamicChildren();

		for (Widget myItemContainerChild : myItemContainerChildren)
		{
			if (myItemContainerChild.getItemId() != 0 && !myItemContainerChild.isHidden())
			{
				Item item = new Item(myItemContainerChild.getItemId(), myItemContainerChild.getItemQuantity());
				int itemPrice = itemManager.getItemPrice(item.getId());
				int itemQuantity = item.getQuantity();
				myItemPrice = myItemPrice + itemPrice * itemQuantity;
			}
		}

		Widget[] theirItemContainerChildren = theirItemContainer.getDynamicChildren();

		for (Widget theirItemContainerChild: theirItemContainerChildren)
		{
			if (theirItemContainerChild.getItemId() != 0 && !theirItemContainerChild.isHidden())
			{
				Item item = new Item(theirItemContainerChild.getItemId(), theirItemContainerChild.getItemQuantity());
				int itemPrice = itemManager.getItemPrice(item.getId());
				int itemQuantity = item.getQuantity();
				theirItemsPrice = theirItemsPrice + itemPrice * itemQuantity;
			}
		}

		Widget myTradeOffer = client.getWidget(WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID, 24);
		if (myTradeOffer == null)
		{
			return;
		}
		Widget theirTradeOffer = client.getWidget(WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID, 27);
		if (theirTradeOffer == null)
		{
			return;
		}

		int theirColor = BALANCED_FAVORED_TRADE;
		if (myItemPrice > theirItemsPrice)
		{
			theirColor = IMBALANCED_TRADE;
		}
		String myTradeText = "You offer:<br>(Value: <col=ffffff>" + (myItemPrice == 1 ? "One" : format(myItemPrice)) + "</col> " + (myItemPrice == 1 ? "coin" : "coins") + ")";

		String theirName = theirTradeOffer.getText().substring(0, theirTradeOffer.getText().indexOf(" offers:<br>"));
		String theirTradeText = theirName + " offers:<br>(Value: <col=ffffff>" + (theirItemsPrice == 1 ? "One" : format(theirItemsPrice)) + "</col> " + (theirItemsPrice == 1 ? "coin" : "coins") + ")";

		myTradeOffer.setText(myTradeText);
		theirTradeOffer.setText(theirTradeText);
		theirTradeOffer.setTextColor(theirColor);
	}

	// do second screen stuff
	public void secondTradeScreen() throws Exception
	{

		Widget theirSecondTradeText = client.getWidget(334, 24);
		if (theirSecondTradeText == null)
		{
			return;
		}

		Widget mySecondTradeText = client.getWidget(334, 23);
		if (mySecondTradeText == null)
		{
			return;
		}

		String myTradeText = "You are about to give:<br>(Value: <col=ffffff>" + (myItemPrice == 1 ? "One" : format(myItemPrice)) + "</col> " + (myItemPrice == 1 ? "coin" : "coins") + ")";
		String theirTradeText = "In return you will receive:<br>(Value: <col=" + (myItemPrice > theirItemsPrice ? RED_COLOR : WHITE_COLOR) + ">" + (theirItemsPrice == 1 ? "One" : format(theirItemsPrice)) + "</col> " + (theirItemsPrice == 1 ? "coin" : "coins") + ")";

		mySecondTradeText.setText(myTradeText);
		theirSecondTradeText.setText(theirTradeText);
	}

	public String format(long number) {
		return NumberFormat.getNumberInstance(Locale.UK).format(number);
	}
}