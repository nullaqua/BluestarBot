package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.message.Message;
import me.lanzhi.bluestarbot.api.message.MessageChain;
import me.lanzhi.bluestarbot.api.message.Reply;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.Date;

/**
 * 被动接受消息事件
 */
public interface MessageReceivedEvent
{
    public MessageEvent getEvent();

    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * 快捷进行回复
     *
     * @param message 消息
     */
    public default void reply(Message message)
    {
        getContact().sendMessage(message,new Reply(this));
    }

    /**
     * @return 所属聊天
     */
    public default Contact getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    /**
     * 快捷进行回复纯文本消息
     *
     * @param message 纯文本消息
     */
    public default void reply(String message)
    {
        getContact().sendMessage(Message.getFromMiraiCode(message),new Reply(this));
    }

    /**
     * @return mirai码样式的消息
     * @deprecated 不建议通过Mirai码处理,建议使用 {@link #getMessage()} 进行处理
     */
    @Deprecated(since="1.4.0")
    public default String getMessageAsCode()
    {
        return getEvent().getMessage().serializeToMiraiCode();
    }

    /**
     * @return 消息显示样式
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
        return Mapping.map(getEvent().getMessage());
    }

    /**
     * 创建一个对此消息的回复
     *
     * @return 创建的回复
     * @see Contact#sendMessage(Message,Reply) 可通过此方法发送
     */
    public default Reply createReply()
    {
        return new Reply(this);
    }

    /**
     * 获取此消息回复的内容
     */
    public default Reply getReply()
    {
        QuoteReply reply=getEvent().getMessage().get(QuoteReply.Key);
        if (reply==null)
        {
            return null;
        }
        else
        {
            return new Reply(reply);
        }
    }

    /**
     * @return 发送人名称
     */
    public default String getSenderName()
    {
        return getSender().getName();
    }

    /**
     * @return 发送人
     */
    public default User getSender()
    {
        return Mapping.map(getEvent().getSender());
    }

    /**
     * @return 发送时间
     */
    public default Date getTime()
    {
        return new Date(1000L*getEvent().getTime());
    }
}
