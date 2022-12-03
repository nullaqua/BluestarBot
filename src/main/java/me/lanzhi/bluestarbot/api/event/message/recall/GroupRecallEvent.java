package me.lanzhi.bluestarbot.api.event.message.recall;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.GroupMember;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.api.event.MessageRecallEvent;

public final class GroupRecallEvent extends BluestarBotEvent implements MessageRecallEvent, GroupMemberEvent
{
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

    @Override
    public net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall getEvent()
    {
        return (net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall) super.getEvent();
    }
}
