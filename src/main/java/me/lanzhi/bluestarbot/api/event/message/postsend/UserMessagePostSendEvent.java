package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.User;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.MessagePostSendEvent;
import me.lanzhi.bluestarbot.api.event.UserEvent;
import net.mamoe.mirai.message.data.MessageSource;

public abstract class UserMessagePostSendEvent extends BluestarBotEvent implements MessagePostSendEvent, UserEvent
{
    public UserMessagePostSendEvent(net.mamoe.mirai.event.events.UserMessagePostSendEvent<?> event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.UserMessagePostSendEvent<?> getEvent()
    {
        return (net.mamoe.mirai.event.events.UserMessagePostSendEvent<?>) super.getEvent();
    }

    @Override
    public void recall()
    {
        MessageSource.recall(getEvent().getMessage());
    }

    @Override
    public User getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public User getUser()
    {
        return getContact();
    }
}
