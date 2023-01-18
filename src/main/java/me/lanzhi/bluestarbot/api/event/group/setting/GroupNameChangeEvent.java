package me.lanzhi.bluestarbot.api.event.group.setting;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupSettingChangeEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群名称变化事件
 */
public final class GroupNameChangeEvent extends BluestarBotEvent implements GroupSettingChangeEvent
{
    @Internal
    public GroupNameChangeEvent(net.mamoe.mirai.event.events.GroupNameChangeEvent event)
    {
        super(event);
    }

    @Internal
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

    /**
     * @return 操作人
     */
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
