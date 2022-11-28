package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;

public final class FriendInputStatusChangedEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendInputStatusChangedEvent(net.mamoe.mirai.event.events.FriendInputStatusChangedEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.FriendInputStatusChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendInputStatusChangedEvent) super.getEvent();
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    public boolean isInputting()
    {
        return getEvent().getInputting();
    }
}
