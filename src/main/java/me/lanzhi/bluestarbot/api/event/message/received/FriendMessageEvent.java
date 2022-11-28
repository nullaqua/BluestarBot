package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;

public final class FriendMessageEvent extends UserMessageEvent implements MessageReceivedEvent, FriendEvent
{
    public FriendMessageEvent(net.mamoe.mirai.event.events.FriendMessageEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.FriendMessageEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendMessageEvent) super.getEvent();
    }

    @Override
    public Friend getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    @Override
    public Friend getSender()
    {
        return Mapping.map(getEvent().getSender());
    }

    @Override
    public Friend getUser()
    {
        return getSender();
    }
}
