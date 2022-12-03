package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Contact;
import me.lanzhi.bluestarbot.api.User;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSource;

public interface MessageReceivedEvent
{
    public MessageEvent getEvent();

    public Bot getBot();

    public default void reply(String message)
    {
        MessageSource source=getEvent().getMessage().get(MessageSource.Key);
        assert source!=null:"assertError:MessageSource is null(MessageReceivedEvent:50)";
        getContact().reply(getSender(),
                           getTime(),
                           getMessageAsCode(),
                           message,
                           source.getIds()[0],
                           (int) source.getTargetId());
    }

    public default String getMessage()
    {
        return getEvent().getMessage().contentToString();
    }

    public default String getMessageAsCode()
    {
        return getEvent().getMessage().serializeToMiraiCode();
    }

    public default Contact getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    public default User getSender()
    {
        return Mapping.map(getEvent().getSender());
    }

    public default String getSenderName()
    {
        return getSender().getName();
    }

    public default long getTime()
    {
        return getEvent().getTime()*1000L;
    }
}
