package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import net.mamoe.mirai.message.data.MessageSource;

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
    public void recall()
    {
        MessageSource.recall(getEvent().getMessage());
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
