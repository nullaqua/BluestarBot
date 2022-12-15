package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 好友昵称改变事件
 */
public final class FriendNickChangedEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendNickChangedEvent(net.mamoe.mirai.event.events.FriendNickChangedEvent event)
    {
        super(event);
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    @Override
    public net.mamoe.mirai.event.events.FriendNickChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendNickChangedEvent) super.getEvent();
    }

    /**
     * @return 旧昵称
     */
    public String getOld()
    {
        return getEvent().getFrom();
    }

    /**
     * @return 新昵称
     */
    public String getNew()
    {
        return getEvent().getTo();
    }
}
