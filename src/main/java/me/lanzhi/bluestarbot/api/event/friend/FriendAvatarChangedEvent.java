package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;

public final class FriendAvatarChangedEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendAvatarChangedEvent(net.mamoe.mirai.event.events.FriendAvatarChangedEvent event)
    {
        super(event);
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    @Override
    public net.mamoe.mirai.event.events.FriendAvatarChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendAvatarChangedEvent) super.getEvent();
    }
}
