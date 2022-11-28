package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;

public final class MemberJoinEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberJoinEvent(net.mamoe.mirai.event.events.MemberJoinEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.MemberJoinEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberJoinEvent) super.getEvent();
    }

    @Override
    public NormalGroupMember getMember()
    {
        return getUser();
    }

    @Override
    public NormalGroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }
}
