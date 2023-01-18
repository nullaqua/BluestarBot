package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

/**
 * 机器人昵称修改事件
 *
 * @see Bot#getNick() 获取昵称
 */
public final class BotNickChangedEvent extends BluestarBotEvent implements BotEvent
{    @Internal
    public BotNickChangedEvent(net.mamoe.mirai.event.events.BotNickChangedEvent event)
    {
        super(event);
    }

    /**
     * 获取原昵称
     *
     * @return 原昵称
     */
    public String getOld()
    {
        return getEvent().getFrom();
    }
    @Internal
    @Override
    public net.mamoe.mirai.event.events.BotNickChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotNickChangedEvent) super.getEvent();
    }

    /**
     * 新昵称
     *
     * @return 新昵称
     */
    public String getNew()
    {
        return getEvent().getTo();
    }
}
