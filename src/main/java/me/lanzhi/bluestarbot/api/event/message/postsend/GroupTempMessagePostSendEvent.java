package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群临时会话消息发送事件
 */
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
