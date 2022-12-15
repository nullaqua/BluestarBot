package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 好友输入状态改变事件
 */
public final class FriendInputStatusChangedEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendInputStatusChangedEvent(net.mamoe.mirai.event.events.FriendInputStatusChangedEvent event)
    {
        super(event);
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    @Override
    public net.mamoe.mirai.event.events.FriendInputStatusChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendInputStatusChangedEvent) super.getEvent();
    }

    /**
     * 是否正在输入中
     *
     * @return true表示正在输入
     */
    public boolean isInputting()
    {
        return getEvent().getInputting();
    }
}
