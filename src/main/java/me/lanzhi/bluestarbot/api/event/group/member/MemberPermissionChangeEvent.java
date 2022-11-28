package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.MemberPermission;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;

public final class MemberPermissionChangeEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberPermissionChangeEvent(net.mamoe.mirai.event.events.MemberPermissionChangeEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.MemberPermissionChangeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberPermissionChangeEvent) super.getEvent();
    }

    public MemberPermission getNew()
    {
        return Mapping.map(getEvent().getNew());
    }

    public MemberPermission getOld()
    {
        return Mapping.map(getEvent().getOrigin());
    }

    @Override
    public NormalGroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }
}
