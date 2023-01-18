package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Stranger;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.api.event.StrangerEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 接收到陌生人消息
 */
public final class StrangerMessageEvent extends UserMessageEvent implements StrangerEvent, MessageReceivedEvent
{
    @Internal
    @Override
    public net.mamoe.mirai.event.events.StrangerMessageEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.StrangerMessageEvent) super.getEvent();
    }

    @Internal
    public StrangerMessageEvent(net.mamoe.mirai.event.events.StrangerMessageEvent event)
    {
        super(event);
    }

    @Override
    public Stranger getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    @Override
    public Stranger getSender()
    {
        return Mapping.map(getEvent().getSender());
    }

    @Override
    public Stranger getUser()
    {
        return getStranger();
    }
}
