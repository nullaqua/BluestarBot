package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.User;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.api.event.UserEvent;

public abstract class UserMessageEvent extends BluestarBotEvent implements UserEvent, MessageReceivedEvent
{
    public UserMessageEvent(net.mamoe.mirai.event.events.UserMessageEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.UserMessageEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.UserMessageEvent) super.getEvent();
    }

    @Override
    public User getContact()
    {
        return getUser();
    }

    @Override
    public User getSender()
    {
        return getUser();
    }

    @Override
    public User getUser()
    {
        return Mapping.map(getEvent().getSender());
    }
}
