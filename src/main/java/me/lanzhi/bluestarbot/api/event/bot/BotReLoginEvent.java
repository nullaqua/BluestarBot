package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

/**
 * 机器人重新登录事件
 */
public final class BotReLoginEvent extends BluestarBotEvent implements BotEvent
{
    public BotReLoginEvent(net.mamoe.mirai.event.events.BotReloginEvent event)
    {
        super(event);
    }

    /**
     * 原因
     *
     * @return 原因
     */
    public Throwable getCause()
    {
        return getEvent().getCause();
    }

    @Override
    public net.mamoe.mirai.event.events.BotReloginEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotReloginEvent) super.getEvent();
    }
}
