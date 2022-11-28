package me.lanzhi.bluestarbot.api.event.message.presend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.api.event.MessagePreSendEvent;

public final class GroupMessagePreSendEvent extends BluestarBotEvent implements MessagePreSendEvent, GroupEvent
{
    public GroupMessagePreSendEvent(net.mamoe.mirai.event.events.GroupMessagePreSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupMessagePreSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupMessagePreSendEvent) super.getEvent();
    }

    @Override
    public Group getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public Group getGroup()
    {
        return getContact();
    }
}
