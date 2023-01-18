package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.MemberPermission;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群成员权限修改事件
 *
 * @see MemberPermission 群员权限
 */
public final class MemberPermissionChangeEvent extends BluestarBotEvent implements GroupMemberEvent
{
    @Internal
    public MemberPermissionChangeEvent(net.mamoe.mirai.event.events.MemberPermissionChangeEvent event)
    {
        super(event);
    }

    public MemberPermission getNew()
    {
        return Mapping.map(getEvent().getNew());
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.MemberPermissionChangeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberPermissionChangeEvent) super.getEvent();
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
