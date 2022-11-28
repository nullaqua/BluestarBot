package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import net.mamoe.mirai.message.data.MessageSource;

public final class GroupTempMessagePostSendEvent extends UserMessagePostSendEvent implements GroupMemberEvent
{
    public GroupTempMessagePostSendEvent(net.mamoe.mirai.event.events.GroupTempMessagePostSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupTempMessagePostSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupTempMessagePostSendEvent) super.getEvent();
    }

    @Override
    public void recall()
    {
        MessageSource.recall(getEvent().getMessage());
    }

    @Override
    public NormalGroupMember getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public NormalGroupMember getUser()
    {
        return getContact();
    }
}
