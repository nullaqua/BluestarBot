package me.lanzhi.bluestarbot.api.event.group.setting;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupSettingChangeEvent;

public final class GroupNameChangeEvent extends BluestarBotEvent implements GroupSettingChangeEvent
{
    public GroupNameChangeEvent(net.mamoe.mirai.event.events.GroupNameChangeEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupNameChangeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupNameChangeEvent) super.getEvent();
    }

    public String getNew()
    {
        return getEvent().getNew();
    }

    @Override
    public boolean getShouldBroadcast()
    {
        return getEvent().getShouldBroadcast();
    }

    public String getOld()
    {
        return getEvent().getOrigin();
    }

    public NormalGroupMember getOperator()
    {
        return Mapping.map(getEvent().getOperator());
    }

    @Override
    public Group getGroup()
    {
        return Mapping.map(getEvent().getGroup());
    }
}
