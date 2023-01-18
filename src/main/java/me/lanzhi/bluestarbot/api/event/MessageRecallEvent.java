package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.internal.Mapping;

import java.util.Date;

/**
 * 消息撤回事件
 */
public interface MessageRecallEvent
{
    @Internal
    public net.mamoe.mirai.event.events.MessageRecallEvent getEvent();

    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * @return 发送人
     */
    public default User getSender()
    {
        return Mapping.map(getEvent().getAuthor());
    }

    /**
     * 发送时间
     */
    public default Date getTime()
    {
        return new Date(getEvent().getMessageTime()*1000L);
    }
}
