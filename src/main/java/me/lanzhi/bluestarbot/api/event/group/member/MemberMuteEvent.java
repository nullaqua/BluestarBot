package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.api.contact.group.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群员被禁言事件
 */
public final class MemberMuteEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberMuteEvent(net.mamoe.mirai.event.events.MemberMuteEvent event)
    {
        super(event);
    }

    /**
     * @return 禁言时间
     */
    public int getDurationSeconds()
    {
        return getEvent().getDurationSeconds();
    }

    @Override
    public net.mamoe.mirai.event.events.MemberMuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberMuteEvent) super.getEvent();
    }

    /**
     * @return 操作人
     */
    public GroupMember getOperator()
    {
        return Mapping.map(getEvent().getOperator());
    }

    /**
     * @return 被禁言人
     */
    @Override
    public GroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }
}
