package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 接受到群临时会话消息
 */
public final class GroupTempMessageEvent extends UserMessageEvent implements MessageReceivedEvent,GroupMemberEvent
{
    @Internal
    public GroupTempMessageEvent(net.mamoe.mirai.event.events.GroupTempMessageEvent event)
    {
        super(event);
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.GroupTempMessageEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupTempMessageEvent) super.getEvent();
    }

    @Override
    public NormalGroupMember getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    @Override
    public NormalGroupMember getSender()
    {
        return Mapping.map(getEvent().getSender());
    }

    @Override
    public Group getGroup()
    {
        return getSender().getGroup();
    }

    @Override
    public NormalGroupMember getUser()
    {
        return getSender();
    }
}
