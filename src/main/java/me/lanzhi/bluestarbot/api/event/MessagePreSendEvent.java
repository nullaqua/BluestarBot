package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Contact;
import net.mamoe.mirai.message.code.MiraiCode;
import org.bukkit.event.Cancellable;

public interface MessagePreSendEvent extends Cancellable
{
    public net.mamoe.mirai.event.events.MessagePreSendEvent getEvent();

    public default Contact getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    public default String getMessageAsCode()
    {
        return getEvent().getMessage().toString();
    }

    public default String getMessage()
    {
        return getEvent().getMessage().contentToString();
    }

    public default void setMessage(String message)
    {
        getEvent().setMessage(MiraiCode.deserializeMiraiCode(message));
    }

    public Bot getBot();
}
