package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;

public final class FriendDeleteEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendDeleteEvent(net.mamoe.mirai.event.events.FriendDeleteEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.FriendDeleteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendDeleteEvent) super.getEvent();
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }
}