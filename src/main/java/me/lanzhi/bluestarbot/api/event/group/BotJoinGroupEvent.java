package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;

public final class BotJoinGroupEvent extends BluestarBotEvent implements GroupEvent
{
    public BotJoinGroupEvent(net.mamoe.mirai.event.events.BotJoinGroupEvent event)
    {
        super(event);
    }

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
