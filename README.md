# ZMI Cycle Tracker
Counts how many ZMI laps remain until your pouches degrade.

Resets on NPC Contact spell cast.
Counter decrements the first time you craft runes after entering ZMI.

Default reset value is 8, for the Colossal pouch.
11, 29, and 45 are also selectable in the configuration for Giant, Large, and Medium pouches, respectively.
Configuration can be changed freely if you have not crafted runes since your last NPC contact, or since you logged in.
Otherwise, the configuration will update the reset value but not modify the counter.

This is not so easily expandable to all RC methods, as it's difficult to say what exactly 1 lap means.
My best guess would be to have the counter decrement the first time you craft runes after accessing a bank, but this wouldn't work in places like Guardians of the Rift.
So while we are being arbitrary, this plugin only works for ZMI. :)

This plugin was created as other essence tracking plugins tend to be unreliable in my experience.
So my idea was to circumvent the pouch entirely and instead infer a lap was taking place, which CAN be definitively tracked.