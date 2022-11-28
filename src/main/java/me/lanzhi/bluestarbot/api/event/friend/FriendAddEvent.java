package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;

public final class FriendAddEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendAddEvent(net.mamoe.mirai.event.events.FriendAddEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.FriendAddEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendAddEvent) super.getEvent();
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }
}
