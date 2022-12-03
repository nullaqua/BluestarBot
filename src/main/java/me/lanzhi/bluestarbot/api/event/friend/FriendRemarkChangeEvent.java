package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;

public final class FriendRemarkChangeEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendRemarkChangeEvent(net.mamoe.mirai.event.events.FriendRemarkChangeEvent event)
    {
        super(event);
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    @Override
    public net.mamoe.mirai.event.events.FriendRemarkChangeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendRemarkChangeEvent) super.getEvent();
    }

    public String getOld()
    {
        return getEvent().getOldRemark();
    }

    public String getNew()
    {
        return getEvent().getNewRemark();
    }
}
