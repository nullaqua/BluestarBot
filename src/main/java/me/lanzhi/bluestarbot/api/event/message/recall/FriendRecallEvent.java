package me.lanzhi.bluestarbot.api.event.message.recall;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.api.event.MessageRecallEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 好友消息撤回
 */
public final class FriendRecallEvent extends BluestarBotEvent implements MessageRecallEvent, FriendEvent
{
    public FriendRecallEvent(net.mamoe.mirai.event.events.MessageRecallEvent.FriendRecall event)
    {
        super(event);
    }

    @Override
    public Friend getUser()
    {
        return getSender();
    }

    @Override
    public Friend getSender()
    {
        return Mapping.map(getEvent().getAuthor());
    }

    @Override
    public net.mamoe.mirai.event.events.MessageRecallEvent.FriendRecall getEvent()
    {
        return (net.mamoe.mirai.event.events.MessageRecallEvent.FriendRecall) super.getEvent();
    }
}
