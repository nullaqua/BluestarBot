package me.lanzhi.bluestarbot.api.event.message.presend;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.api.event.MessagePreSendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 好友
 */
public final class FriendMessagePreSendEvent extends UserMessagePreSendEvent implements MessagePreSendEvent, FriendEvent
{
    public FriendMessagePreSendEvent(net.mamoe.mirai.event.events.FriendMessagePreSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.FriendMessagePreSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendMessagePreSendEvent) super.getEvent();
    }

    @Override
    public Friend getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public Friend getUser()
    {
        return getContact();
    }
}
