package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 机器人被禁言事件
 */
public final class BotMuteEvent extends BluestarBotEvent implements GroupEvent
{
    @Internal
    public BotMuteEvent(net.mamoe.mirai.event.events.BotMuteEvent event)
    {
        super(event);
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.BotMuteEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotMuteEvent) super.getEvent();
    }

    /**
     * @return 被禁言的时间,单位:秒
     */
    public int getDurationSeconds()
    {
        return getEvent().getDurationSeconds();
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
