package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;

public final class BotUnmuteEvent extends BluestarBotEvent implements GroupEvent
{
    public BotUnmuteEvent(net.mamoe.mirai.event.events.BotUnmuteEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BotUnmuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotUnmuteEvent) super.getEvent();
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
