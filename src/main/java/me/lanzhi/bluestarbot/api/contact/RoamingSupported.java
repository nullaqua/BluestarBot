package me.lanzhi.bluestarbot.api.contact;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.message.MessageId;
import me.lanzhi.bluestarbot.api.message.MessageWithId;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 支持漫游聊天记录的联系人,目前为{@link Friend}和{@link me.lanzhi.bluestarbot.api.contact.group.Group}支持
 */
public interface RoamingSupported
{
    /**
     * 获取漫游聊天记录
     *
     * @param fromTime 起始时间
     * @param toTime   结束时间
     * @param filter   过滤器
     * @return 漫游聊天记录
     */
    default Stream<MessageWithId> getRoamingMessage(long fromTime,long toTime,Predicate<MessageWithId> filter)
    {
        if (filter==null)
            return getRoamingMessage(fromTime,toTime);
        return getRoamingMessage(fromTime,toTime).filter(filter);
    }

    /**
     * 获取漫游聊天记录
     *
     * @param fromTime 起始时间
     * @param toTime   结束时间
     * @return 漫游聊天记录
     */
    default Stream<MessageWithId> getRoamingMessage(long fromTime,long toTime)
    {
        return getRoamingSupported().getRoamingMessages().getMessagesStream(fromTime,toTime).map(MessageWithId::map);
    }

    @Internal
    net.mamoe.mirai.contact.roaming.RoamingSupported getRoamingSupported();

    /**
     * 获取漫游聊天记录
     *
     * @param fromTime 起始时间
     * @param filter   过滤器
     * @return 漫游聊天记录
     */
    default Stream<MessageWithId> getRoamingMessage(long fromTime,Predicate<MessageWithId> filter)
    {
        if (filter==null)
            return getRoamingMessage(fromTime);
        return getRoamingMessage(fromTime).filter(filter);
    }

    /**
     * 获取漫游聊天记录
     *
     * @param fromTime 起始时间
     * @return 漫游聊天记录
     */
    default Stream<MessageWithId> getRoamingMessage(long fromTime)
    {
        return getRoamingMessage(fromTime,System.currentTimeMillis());
    }

    /**
     * 通过消息id获取漫游聊天记录
     * <p>如果消息id不存在,则返回null</p>
     *
     * @param id 消息id
     * @return 漫游聊天记录
     */
    default MessageWithId getMessageById(MessageId id)
    {
        return getRoamingMessage(messageWithId->messageWithId.id.equals(id)).findFirst().orElse(null);
    }

    /**
     * 获取漫游聊天记录
     *
     * @param filter 过滤器
     * @return 漫游聊天记录
     */
    default Stream<MessageWithId> getRoamingMessage(Predicate<MessageWithId> filter)
    {
        if (filter==null)
            return getRoamingMessage();
        return getRoamingMessage().filter(filter);
    }

    /**
     * 获取漫游聊天记录
     *
     * @return 漫游聊天记录
     */
    default Stream<MessageWithId> getRoamingMessage()
    {
        return getRoamingSupported().getRoamingMessages().getAllMessagesStream().map(MessageWithId::map);
    }
}