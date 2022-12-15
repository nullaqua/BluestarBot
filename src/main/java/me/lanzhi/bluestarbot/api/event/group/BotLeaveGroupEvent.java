package me.lanzhi.bluestarbot.api.event.group;

import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.event.events.BotLeaveEvent;

/**
 * 机器人离开群聊事件
 */
public final class BotLeaveGroupEvent extends BluestarBotEvent implements GroupEvent
{
    public BotLeaveGroupEvent(BotLeaveEvent event)
    {
        super(event);
    }

    /**
     * @return 离开原因
     * @see Type 原因类型
     */
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
    public BotLeaveEvent getEvent()
    {
        return (BotLeaveEvent) super.getEvent();
    }

    /**
     * @return 群聊
     */
    @Override
    public Group getGroup()
    {
        return Mapping.map(getEvent().getGroup());
    }

    public enum Type
    {
        /**
         * 主动退出
         */
        ACTIVE,
        /**
         * 被踢出
         */
        KICK,
        /**
         * 群聊被解散
         */
        DISBAND,
        UNKNOWN
    }
}
