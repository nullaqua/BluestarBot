package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Friend;

public interface FriendEvent extends UserEvent
{
    public Bot getBot();

    public default Friend getFriend()
    {
        return getUser();
    }

    @Override
    public Friend getUser();
}
