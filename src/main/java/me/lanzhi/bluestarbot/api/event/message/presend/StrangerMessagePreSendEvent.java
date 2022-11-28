package me.lanzhi.bluestarbot.api.event.message.presend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Stranger;
import me.lanzhi.bluestarbot.api.event.MessagePreSendEvent;
import me.lanzhi.bluestarbot.api.event.StrangerEvent;

public final class StrangerMessagePreSendEvent extends UserMessagePreSendEvent implements MessagePreSendEvent, StrangerEvent
{
    public StrangerMessagePreSendEvent(net.mamoe.mirai.event.events.StrangerMessagePreSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.StrangerMessagePreSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.StrangerMessagePreSendEvent) super.getEvent();
    }

    @Override
    public Stranger getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public Stranger getUser()
    {
        return getContact();
    }
}
