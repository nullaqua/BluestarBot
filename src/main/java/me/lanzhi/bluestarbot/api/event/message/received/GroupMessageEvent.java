package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;

public final class GroupMessageEvent extends BluestarBotEvent implements MessageReceivedEvent, GroupMemberEvent
{
    public GroupMessageEvent(net.mamoe.mirai.event.events.GroupMessageEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupMessageEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupMessageEvent) super.getEvent();
    }

    @Override
    public Group getContact()
    {
        return getGroup();
    }

    @Override
    public GroupMember getSender()
    {
        return Mapping.map(getEvent().getSender());
    }

    @Override
    public Group getGroup()
    {
        return getContact();
    }

    @Override
    public GroupMember getUser()
    {
        return getSender();
    }
}
