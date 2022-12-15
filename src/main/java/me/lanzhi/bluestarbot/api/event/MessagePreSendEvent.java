package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.message.MessageChain;
import me.lanzhi.bluestarbot.internal.Mapping;
import me.lanzhi.bluestarbot.internal.message.MessageChainImpl;
import org.bukkit.event.Cancellable;

/**
 * 消息预发送事件
 */
public interface MessagePreSendEvent extends Cancellable
{
    /**
     * @return mirai码样式的消息
     * @see MessagePreSendEvent#getMessage() 建议使用的方法
     * @deprecated
     */
    @Deprecated
    public default String getMessageAsCode()
    {
        return getEvent().getMessage().toString();
    }

    public net.mamoe.mirai.event.events.MessagePreSendEvent getEvent();

    /**
     * @return 消息的显示样式
     */
    public default String getMessageAsString()
    {
        return getMessage().getDisplay(getContact());
    }

    /**
     * @return 消息
     */
    public default MessageChain getMessage()
    {
        return Mapping.map(getEvent().getMessage().plus(""));
    }

    /**
     * @return 所属聊天
     */
    public default Contact getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    /**
     * 设置发送的消息
     *
     * @param message 消息
     */
    public default void setMessage(MessageChain message)
    {
        getEvent().setMessage(((MessageChainImpl) message).toMirai());
    }

    /**
     * @return 所属机器人
     */
    public Bot getBot();
}
