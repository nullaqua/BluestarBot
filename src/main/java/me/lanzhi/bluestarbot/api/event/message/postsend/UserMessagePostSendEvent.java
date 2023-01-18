package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.MessagePostSendEvent;
import me.lanzhi.bluestarbot.api.event.UserEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 向用户私聊发送消息事件
 */
public abstract class UserMessagePostSendEvent extends BluestarBotEvent implements MessagePostSendEvent, UserEvent
{
    @Internal
    public UserMessagePostSendEvent(net.mamoe.mirai.event.events.UserMessagePostSendEvent<?> event)
    {
        super(event);
    }

    @Override
    public User getUser()
    {
        return getContact();
    }

    @Override
    public User getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.UserMessagePostSendEvent<?> getEvent()
    {
        return (net.mamoe.mirai.event.events.UserMessagePostSendEvent<?>) super.getEvent();
    }
}
