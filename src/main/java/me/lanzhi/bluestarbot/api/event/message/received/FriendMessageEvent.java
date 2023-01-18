package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 接受到好友消息
 */
public final class FriendMessageEvent extends UserMessageEvent implements MessageReceivedEvent, FriendEvent
{
    @Internal
    public FriendMessageEvent(net.mamoe.mirai.event.events.FriendMessageEvent event)
    {
        super(event);
    }

    @Internal
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
