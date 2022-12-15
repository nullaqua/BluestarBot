package me.lanzhi.bluestarbot.api.event.message.presend;

import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.api.event.MessagePreSendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群聊
 */
public final class GroupMessagePreSendEvent extends BluestarBotEvent implements MessagePreSendEvent, GroupEvent
{
    public GroupMessagePreSendEvent(net.mamoe.mirai.event.events.GroupMessagePreSendEvent event)
    {
        super(event);
    }

    @Override
    public Group getGroup()
    {
        return getContact();
    }

    @Override
    public Group getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public net.mamoe.mirai.event.events.GroupMessagePreSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupMessagePreSendEvent) super.getEvent();
    }
}
