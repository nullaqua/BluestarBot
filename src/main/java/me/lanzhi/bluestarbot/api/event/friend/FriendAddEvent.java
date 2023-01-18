package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 添加了一个好友
 */
public final class FriendAddEvent extends BluestarBotEvent implements FriendEvent
{
    @Internal
    public FriendAddEvent(net.mamoe.mirai.event.events.FriendAddEvent event)
    {
        super(event);
    }

    /**
     * 添加的好友
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
    public net.mamoe.mirai.event.events.FriendAddEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendAddEvent) super.getEvent();
    }
}
