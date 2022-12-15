package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.api.contact.group.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 用户被取消禁言事件
 */
public final class MemberUnmuteEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberUnmuteEvent(net.mamoe.mirai.event.events.MemberUnmuteEvent event)
    {
        super(event);
    }

    /**
     * @return 操作人
     */
    public GroupMember getOperator()
    {
        return Mapping.map(getEvent().getOperator());
    }

    @Override
    public net.mamoe.mirai.event.events.MemberUnmuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberUnmuteEvent) super.getEvent();
    }

    @Override
    public GroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }
}
