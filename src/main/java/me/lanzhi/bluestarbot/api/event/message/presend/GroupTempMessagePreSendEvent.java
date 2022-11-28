package me.lanzhi.bluestarbot.api.event.message.presend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.api.event.MessagePreSendEvent;

public final class GroupTempMessagePreSendEvent extends UserMessagePreSendEvent implements MessagePreSendEvent, GroupMemberEvent
{
    public GroupTempMessagePreSendEvent(net.mamoe.mirai.event.events.GroupTempMessagePreSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupTempMessagePreSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupTempMessagePreSendEvent) super.getEvent();
    }

    @Override
    public NormalGroupMember getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public Group getGroup()
    {
        return getContact().getGroup();
    }

    @Override
    public NormalGroupMember getUser()
    {
        return getContact();
    }
}
