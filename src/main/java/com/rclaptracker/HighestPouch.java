package com.rclaptracker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HighestPouch
{
    MEDIUM("Medium Pouch [45]", 45),
    LARGE("Large Pouch [29]", 29),
    GIANT("Giant Pouch [11]", 11),
    COLOSSAL("Colossal Pouch [8]", 8);

    private final String name;
    private final int target;

    @Override
    public String toString()
    {
        return getName();
    }
}