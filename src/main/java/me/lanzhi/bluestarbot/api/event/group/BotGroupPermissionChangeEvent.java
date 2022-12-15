package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.MemberPermission;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

public final class BotGroupPermissionChangeEvent extends BluestarBotEvent implements GroupEvent
{
    public BotGroupPermissionChangeEvent(net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent) super.getEvent();
    }

    public MemberPermission getNew()
    {
        return Mapping.map(getEvent().getNew());
    }

    public MemberPermission getOld()
    {
        return Mapping.map(getEvent().getOrigin());
    }

    @Override
    public Group getGroup()
    {
        return Mapping.map(getEvent().getGroup());
    }
}
