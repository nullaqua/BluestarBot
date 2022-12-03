package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;

public final class MemberMuteEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberMuteEvent(net.mamoe.mirai.event.events.MemberMuteEvent event)
    {
        super(event);
    }

    public int getDurationSeconds()
    {
        return getEvent().getDurationSeconds();
    }

    @Override
    public net.mamoe.mirai.event.events.MemberMuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberMuteEvent) super.getEvent();
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
