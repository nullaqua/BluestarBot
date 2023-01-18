package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 好友删除事件
 */
public final class FriendDeleteEvent extends BluestarBotEvent implements FriendEvent
{
    @Internal
    public FriendDeleteEvent(net.mamoe.mirai.event.events.FriendDeleteEvent event)
    {
        super(event);
    }

    /**
     * 被删除的好友
     *
     * @return 好友
     */
    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.FriendDeleteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendDeleteEvent) super.getEvent();
    }
}