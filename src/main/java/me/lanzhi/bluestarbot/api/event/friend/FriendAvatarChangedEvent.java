package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 好友头像修改事件
 *
 * @see Friend#getAvatarUrl() 获取头像
 */
public final class FriendAvatarChangedEvent extends BluestarBotEvent implements FriendEvent
{
    @Internal
    public FriendAvatarChangedEvent(net.mamoe.mirai.event.events.FriendAvatarChangedEvent event)
    {
        super(event);
    }

    /**
     * 好友
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
    public net.mamoe.mirai.event.events.FriendAvatarChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendAvatarChangedEvent) super.getEvent();
    }
}
