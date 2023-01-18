package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 机器人被取消禁言事件
 */
public final class BotUnmuteEvent extends BluestarBotEvent implements GroupEvent
{
    @Internal
    public BotUnmuteEvent(net.mamoe.mirai.event.events.BotUnmuteEvent event)
    {
        super(event);
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.BotUnmuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotUnmuteEvent) super.getEvent();
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
        return getOperator().getGroup();
    }
}
