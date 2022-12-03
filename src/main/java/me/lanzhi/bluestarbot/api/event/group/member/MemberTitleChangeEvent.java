package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;

public final class MemberTitleChangeEvent extends BluestarBotEvent implements GroupMemberEvent
{
    public MemberTitleChangeEvent(MemberSpecialTitleChangeEvent event)
    {
        super(event);
    }

    public NormalGroupMember getOperator()
    {
        return Mapping.map(getEvent().getOperator());
    }

    public MemberSpecialTitleChangeEvent getEvent()
    {
        return (MemberSpecialTitleChangeEvent) super.getEvent();
    }

    public String getNew()
    {
        return getEvent().getNew();
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
