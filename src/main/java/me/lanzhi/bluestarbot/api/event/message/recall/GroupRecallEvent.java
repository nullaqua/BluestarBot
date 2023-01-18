package me.lanzhi.bluestarbot.api.event.message.recall;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.GroupMember;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.api.event.MessageRecallEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群消息撤回
 */
public final class GroupRecallEvent extends BluestarBotEvent implements MessageRecallEvent, GroupMemberEvent
{
    @Internal
    public GroupRecallEvent(net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall event)
    {
        super(event);
    }

    @Override
    public GroupMember getUser()
    {
        return getSender();
    }

    @Override
    public Group getGroup()
    {
        return getSender().getGroup();
    }

    @Override
    public NormalGroupMember getSender()
    {
        return Mapping.map(getEvent().getAuthor());
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall getEvent()
    {
        return (net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall) super.getEvent();
    }
}
