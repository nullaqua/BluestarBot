package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

public final class BotNickChangedEvent extends BluestarBotEvent implements BotEvent
{
    public BotNickChangedEvent(net.mamoe.mirai.event.events.BotNickChangedEvent event)
    {
        super(event);
    }

    public String getOld()
    {
        return getEvent().getFrom();
    }

    @Override
    public net.mamoe.mirai.event.events.BotNickChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotNickChangedEvent) super.getEvent();
    }

    public String getNew()
    {
        return getEvent().getTo();
    }
}
