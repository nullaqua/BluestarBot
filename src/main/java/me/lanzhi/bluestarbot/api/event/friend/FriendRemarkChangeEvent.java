package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.FriendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 好友的备注修改事件
 */
public final class FriendRemarkChangeEvent extends BluestarBotEvent implements FriendEvent
{
    public FriendRemarkChangeEvent(net.mamoe.mirai.event.events.FriendRemarkChangeEvent event)
    {
        super(event);
    }

    @Override
    public Friend getUser()
    {
        return Mapping.map(getEvent().getFriend());
    }

    @Override
    public net.mamoe.mirai.event.events.FriendRemarkChangeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.FriendRemarkChangeEvent) super.getEvent();
    }

    /**
     * @return 旧备注
     */
    public String getOld()
    {
        return getEvent().getOldRemark();
    }

    /**
     * @return 新备注
     */
    public String getNew()
    {
        return getEvent().getNewRemark();
    }
}
