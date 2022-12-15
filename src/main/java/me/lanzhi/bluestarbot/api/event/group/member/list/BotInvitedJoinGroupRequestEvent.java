package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 邀请机器人加入群聊事件
 */
public final class BotInvitedJoinGroupRequestEvent extends BluestarBotEvent
{
    public BotInvitedJoinGroupRequestEvent(net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent event)
    {
        super(event);
    }

    /**
     * 同意
     */
    public void accept()
    {
        getEvent().accept();
    }

    @Override
    public net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent) super.getEvent();
    }

    /**
     * 拒绝
     */
    public void ignore()
    {
        getEvent().ignore();
    }

    /**
     * @return 群聊的名称
     */
    public String getGroupName()
    {
        return getEvent().getGroupName();
    }

    /**
     * @return 群聊的ID
     */
    public long getGroupId()
    {
        return getEvent().getGroupId();
    }

    /**
     * @return 邀请人
     */
    public Friend getInvitor()
    {
        return Mapping.map(getEvent().getInvitor());
    }
}
