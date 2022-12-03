package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;

public final class MemberLeaveEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberLeaveEvent(net.mamoe.mirai.event.events.MemberLeaveEvent event)
    {
        super(event);
    }

    @Override
    public GroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }

    @Override
    public net.mamoe.mirai.event.events.MemberLeaveEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberLeaveEvent) super.getEvent();
    }
}
