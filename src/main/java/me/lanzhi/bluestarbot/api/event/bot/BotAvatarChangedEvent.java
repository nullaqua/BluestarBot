package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

/**
 * 机器人头像改变事件
 *
 * @see Bot#getAvatarUrl() 获取头像
 */
public final class BotAvatarChangedEvent extends BluestarBotEvent implements BotEvent
{    @Internal
    public BotAvatarChangedEvent(net.mamoe.mirai.event.events.BotAvatarChangedEvent event)
    {
        super(event);
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.BotAvatarChangedEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotAvatarChangedEvent) super.getEvent();
    }
}
