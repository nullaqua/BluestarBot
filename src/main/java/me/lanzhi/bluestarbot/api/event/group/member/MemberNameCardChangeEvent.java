package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;

/**
 * 群员的群昵称(群名片)修改事件
 */
public final class MemberNameCardChangeEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberNameCardChangeEvent(MemberCardChangeEvent event)
    {
        super(event);
    }

    public String getNew()
    {
        return getEvent().getNew();
    }

    @Override
    public MemberCardChangeEvent getEvent()
    {
        return (MemberCardChangeEvent) super.getEvent();
    }

    public String getOld()
    {
        return getEvent().getOrigin();
    }

    @Override
    public NormalGroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }
}
