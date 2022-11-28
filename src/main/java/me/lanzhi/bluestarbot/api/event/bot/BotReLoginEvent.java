package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

public final class BotReLoginEvent extends BluestarBotEvent implements BotEvent
{
    public BotReLoginEvent(net.mamoe.mirai.event.events.BotReloginEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BotReloginEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotReloginEvent) super.getEvent();
    }

    public Throwable getCause()
    {
        return getEvent().getCause();
    }
}
