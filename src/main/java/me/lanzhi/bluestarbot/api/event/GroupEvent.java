package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Group;

public interface GroupEvent
{
    public Bot getBot();

    public Group getGroup();
}
