package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.OtherClient;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.api.event.OtherClientEvent;

public final class OtherClientMessageEvent extends BluestarBotEvent implements MessageReceivedEvent, OtherClientEvent
{
    public OtherClientMessageEvent(net.mamoe.mirai.event.events.OtherClientMessageEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.OtherClientMessageEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.OtherClientMessageEvent)super.getEvent();
    }

    @Override
    public OtherClient getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    @Override
    public OtherClient getOtherClient()
    {
        return getContact();
    }
}
