package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;

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
