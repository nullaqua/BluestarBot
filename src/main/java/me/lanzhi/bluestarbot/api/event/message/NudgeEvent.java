package me.lanzhi.bluestarbot.api.event.message;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Contact;
import me.lanzhi.bluestarbot.api.User;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;

public final class NudgeEvent extends BluestarBotEvent
{

    public NudgeEvent(net.mamoe.mirai.event.events.NudgeEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.NudgeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.NudgeEvent) super.getEvent();
    }

    public Contact getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    public User getFrom()
    {
        return Mapping.map(getEvent().getFrom());
    }

    public User getTo()
    {
        return Mapping.map(getEvent().getTarget());
    }
}
