package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;

public interface GroupSettingChangeEvent extends GroupEvent
{
    public boolean getShouldBroadcast();

    public Bot getBot();

    public Object getOld();

    public Object getNew();
}
