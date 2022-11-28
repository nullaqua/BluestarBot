package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;

public final class BotGroupLeaveEvent extends BluestarBotEvent implements GroupEvent
{
    public BotGroupLeaveEvent(BotLeaveEvent event)
    {
        super(event);
    }

    @Override
    public BotLeaveEvent getEvent()
    {
        return (BotLeaveEvent) super.getEvent();
    }

    public Type getType()
    {
        if (getEvent() instanceof BotLeaveEvent.Active)
        {
            return Type.ACTIVE;
        }
        if (getEvent() instanceof BotLeaveEvent.Disband)
        {
            return Type.DISBAND;
        }
        if (getEvent() instanceof BotLeaveEvent.Kick)
        {
            return Type.KICK;
        }
        return Type.UNKNOWN;
    }

    @Override
    public Group getGroup()
    {
        return Mapping.map(getEvent().getGroup());
    }

    public enum Type
    {
        ACTIVE,KICK,DISBAND,UNKNOWN
    }
}
