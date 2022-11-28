package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

public final class BotAvatarChangedEvent extends BluestarBotEvent implements BotEvent
{
    public BotAvatarChangedEvent(net.mamoe.mirai.event.events.BotAvatarChangedEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BotAvatarChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotAvatarChangedEvent) super.getEvent();
    }
}
