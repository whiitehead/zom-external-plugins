package com.rc_lap_tracker;

        import java.awt.Color;
        import java.awt.image.BufferedImage;
        import lombok.Getter;
        import lombok.Setter;
        import lombok.ToString;
        import net.runelite.client.plugins.Plugin;
        import net.runelite.client.ui.overlay.infobox.InfoBox;

@ToString
public class jwowWriteableCounter extends InfoBox
{
    @Getter
    @Setter
    public int count;

    public jwowWriteableCounter(BufferedImage image, Plugin plugin, int count)
    {
        super(image, plugin);
    }
    @Override
    public String getText()
    {
        return Integer.toString(getCount());
    }

    @Override
    public Color getTextColor()
    {
        return Color.WHITE;
    }
}