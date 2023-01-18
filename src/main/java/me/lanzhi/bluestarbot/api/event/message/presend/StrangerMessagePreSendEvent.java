package me.lanzhi.bluestarbot.api.event.message.presend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Stranger;
import me.lanzhi.bluestarbot.api.event.MessagePreSendEvent;
import me.lanzhi.bluestarbot.api.event.StrangerEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 陌生人
 */
public final class StrangerMessagePreSendEvent extends UserMessagePreSendEvent implements MessagePreSendEvent, StrangerEvent
{
    @Internal
    public StrangerMessagePreSendEvent(net.mamoe.mirai.event.events.StrangerMessagePreSendEvent event)
    {
        super(event);
    }

    @Internal
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
