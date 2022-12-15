package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 向好友发送消息
 */
public final class FriendMessagePostSendEvent extends UserMessagePostSendEvent implements FriendEvent
{
    public FriendMessagePostSendEvent(net.mamoe.mirai.event.events.FriendMessagePostSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.FriendMessagePostSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendMessagePostSendEvent) super.getEvent();
    }

    @Override
    public Friend getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public Friend getUser()
    {
        return getFriend();
    }

    @Override
    public Friend getFriend()
    {
        return getContact();
    }
}
