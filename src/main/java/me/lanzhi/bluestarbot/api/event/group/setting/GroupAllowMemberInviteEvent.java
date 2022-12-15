package me.lanzhi.bluestarbot.api.event.group.setting;

import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupSettingChangeEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 允许邀请进群状态变化事件
 */
public final class GroupAllowMemberInviteEvent extends BluestarBotEvent implements GroupSettingChangeEvent
{
    public GroupAllowMemberInviteEvent(net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent) super.getEvent();
    }

    public Boolean getNew()
    {
        return getEvent().getNew();
    }

    @Override
    public boolean getShouldBroadcast()
    {
        return getEvent().getShouldBroadcast();
    }

    public Boolean getOld()
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
