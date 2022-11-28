package me.lanzhi.bluestarbot.api.event.group.setting;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupSettingChangeEvent;

public final class GroupAllowAnonymousChatEvent extends BluestarBotEvent implements GroupSettingChangeEvent
{
    public GroupAllowAnonymousChatEvent(net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent) super.getEvent();
    }

    public Boolean getNew()
    {
        return getEvent().getNew();
    }

    @Override
    public boolean getShouldBroadcast()
    {
        return false;
    }

    public Boolean getOld()
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
