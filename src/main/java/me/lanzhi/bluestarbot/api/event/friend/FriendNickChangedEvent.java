package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;

public final class FriendNickChangedEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendNickChangedEvent(net.mamoe.mirai.event.events.FriendNickChangedEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.FriendNickChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendNickChangedEvent) super.getEvent();
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    public String getOld()
    {
        return getEvent().getFrom();
    }

    public String getNew()
    {
        return getEvent().getTo();
    }
}
