package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Bot;
import net.mamoe.mirai.contact.Contact;

public interface MessagePostSendEvent
{
    public net.mamoe.mirai.event.events.MessagePostSendEvent<? extends Contact> getEvent();

    public Bot getBot();

    public default String getMessage()
    {
        return getEvent().getMessage().contentToString();
    }

    public default String getMessageAsCode()
    {
        return getEvent().getMessage().toString();
    }

    public default Throwable getException()
    {
        return getEvent().getException();
    }

    public default me.lanzhi.bluestarbot.api.Contact getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    public default boolean isSuccess()
    {
        return getException()==null;
    }

    public void recall();
}
