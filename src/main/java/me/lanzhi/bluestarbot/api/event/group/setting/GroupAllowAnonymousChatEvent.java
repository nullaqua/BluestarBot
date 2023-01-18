package me.lanzhi.bluestarbot.api.event.group.setting;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupSettingChangeEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群聊允许匿名状态变化事件
 */
public final class GroupAllowAnonymousChatEvent extends BluestarBotEvent implements GroupSettingChangeEvent
{
    @Internal
    public GroupAllowAnonymousChatEvent(net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent event)
    {
        super(event);
    }

    @Internal
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
