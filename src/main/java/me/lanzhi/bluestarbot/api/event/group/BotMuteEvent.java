package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;

public final class BotMuteEvent extends BluestarBotEvent implements GroupEvent
{
    public BotMuteEvent(net.mamoe.mirai.event.events.BotMuteEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BotMuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotMuteEvent) super.getEvent();
    }

    public int getDurationSeconds()
    {
        return getEvent().getDurationSeconds();
    }

    public NormalGroupMember getOperator()
    {
        return Mapping.map(getEvent().getOperator());
    }

    @Override
    public Group getGroup()
    {
        return getOperator().getGroup();
    }
}
