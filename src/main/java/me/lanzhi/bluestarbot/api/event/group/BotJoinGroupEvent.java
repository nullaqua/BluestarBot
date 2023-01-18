package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 机器人加入群聊事件
 */
public final class BotJoinGroupEvent extends BluestarBotEvent implements GroupEvent
{
    @Internal
    public BotJoinGroupEvent(net.mamoe.mirai.event.events.BotJoinGroupEvent event)
    {
        super(event);
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.BotJoinGroupEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotJoinGroupEvent) super.getEvent();
    }

    @Override
    public Group getGroup()
    {
        return Mapping.map(getEvent().getGroup());
    }
}
