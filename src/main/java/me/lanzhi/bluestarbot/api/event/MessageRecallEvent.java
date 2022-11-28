package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.User;

public interface MessageRecallEvent
{
    public net.mamoe.mirai.event.events.MessageRecallEvent getEvent();

    public Bot getBot();

    public default User getSender()
    {
        return Mapping.map(getEvent().getAuthor());
    }

    public default int getTime()
    {
        return getEvent().getMessageTime();
    }
}
