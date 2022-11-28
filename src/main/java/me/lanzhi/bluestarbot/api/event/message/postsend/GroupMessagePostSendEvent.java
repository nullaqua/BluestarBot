package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.api.event.MessagePostSendEvent;
import net.mamoe.mirai.message.data.MessageSource;

public final class GroupMessagePostSendEvent extends BluestarBotEvent implements MessagePostSendEvent, GroupEvent
{
    public GroupMessagePostSendEvent(net.mamoe.mirai.event.events.GroupMessagePostSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupMessagePostSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupMessagePostSendEvent) super.getEvent();
    }

    @Override
    public void recall()
    {
        MessageSource.recall(getEvent().getMessage());
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
