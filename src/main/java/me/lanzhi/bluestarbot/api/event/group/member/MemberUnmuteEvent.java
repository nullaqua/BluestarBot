package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;

public final class MemberUnmuteEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberUnmuteEvent(net.mamoe.mirai.event.events.MemberUnmuteEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.MemberUnmuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberUnmuteEvent) super.getEvent();
    }

    public GroupMember getOperator()
    {
        return Mapping.map(getEvent().getOperator());
    }

    @Override
    public GroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }
}
