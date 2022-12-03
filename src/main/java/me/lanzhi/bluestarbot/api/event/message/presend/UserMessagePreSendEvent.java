package me.lanzhi.bluestarbot.api.event.message.presend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.User;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.MessagePreSendEvent;
import me.lanzhi.bluestarbot.api.event.UserEvent;

public abstract class UserMessagePreSendEvent extends BluestarBotEvent implements UserEvent, MessagePreSendEvent
{
    public UserMessagePreSendEvent(net.mamoe.mirai.event.events.UserMessagePreSendEvent event)
    {
        super(event);
    }

    @Override
    public User getUser()
    {
        return getContact();
    }

    @Override
    public User getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public net.mamoe.mirai.event.events.UserMessagePreSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.UserMessagePreSendEvent) super.getEvent();
    }
}
