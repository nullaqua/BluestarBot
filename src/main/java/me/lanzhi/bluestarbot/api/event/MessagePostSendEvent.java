package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.message.MessageChain;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageSource;

import java.util.function.BooleanSupplier;

/**
 * 消息发送事件,发送后触发
 */
public interface MessagePostSendEvent
{
    /**
     * @return 所属机器人
     */
    public Bot getBot();

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
        return Mapping.map(getEvent().getMessage());
    }

    /**
     * @return 所属聊天频道
     */
    public default me.lanzhi.bluestarbot.api.contact.Contact getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    public net.mamoe.mirai.event.events.MessagePostSendEvent<? extends Contact> getEvent();

    /**
     * @return mirai码样式的消息
     * @see MessagePostSendEvent#getMessage() 建议使用的方法
     * @deprecated
     */
    @Deprecated
    public default String getMessageAsCode()
    {
        return getEvent().getMessage().toString();
    }

    /**
     * @return 是否发送成功
     */
    public default boolean isSuccess()
    {
        return getException()==null;
    }

    /**
     * @return 发送时的错误,成功发送时为null
     */
    public default Throwable getException()
    {
        return getEvent().getException();
    }

    /**
     * 撤回消息
     *
     * @return 撤回成功为true
     */
    public default boolean recall()
    {
        try
        {
            MessageSource.recall(getEvent().getMessage());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 定时撤回消息,此操作不会堵塞线程,而是启动另一线程,返回BooleanSupplier用于获取结果
     * 通过BooleanSupplier.getAsBoolean()方法获取操作是否成功,此方法可能堵塞线程,直至撤回完成或失败
     *
     * @param seconds 时间,单位:秒
     * @return 撤回操作结果获取器
     */
    public default BooleanSupplier recall(int seconds)
    {
        try
        {
            return MessageSource.recallIn(getEvent().getMessage(),seconds)::awaitIsSuccess;
        }
        catch (Exception e)
        {
            return ()->false;
        }
    }
}
