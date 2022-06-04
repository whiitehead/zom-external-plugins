# RC Lap Tracker
Counts how many runecrafting laps remain until your pouches degrade.

Resets on NPC Contact spell cast.
Counter decrements the first time you craft runes after banking.

Default reset value is 8, for the Colossal pouch.
11, 29, and 45 are also selectable in the configuration for Giant, Large, and Medium pouches, respectively.
Configuration can be changed freely if you have not crafted runes since your last NPC contact, or since you logged in.
Otherwise, the configuration will update the reset value but not modify the counter.

This plugin was created as other essence tracking plugins tend to be unreliable in my experience.
So my idea was to circumvent the pouch entirely and instead infer a lap was taking place, which CAN be definitively tracked.