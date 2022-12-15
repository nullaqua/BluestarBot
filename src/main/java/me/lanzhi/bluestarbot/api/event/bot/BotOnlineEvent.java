package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

/**
 * 机器人登录事件
 */
public final class BotOnlineEvent extends BluestarBotEvent implements BotEvent
{
    public BotOnlineEvent(net.mamoe.mirai.event.events.BotOnlineEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BotOnlineEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotOnlineEvent) super.getEvent();
    }
}
