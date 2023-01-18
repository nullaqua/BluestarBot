package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Stranger;
import me.lanzhi.bluestarbot.api.event.StrangerEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 陌生人消息发送事件
 */
public final class StrangerMessagePostSendEvent extends UserMessagePostSendEvent implements StrangerEvent
{
    @Internal
    public StrangerMessagePostSendEvent(net.mamoe.mirai.event.events.StrangerMessagePostSendEvent event)
    {
        super(event);
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.StrangerMessagePostSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.StrangerMessagePostSendEvent) super.getEvent();
    }

    @Override
    public Stranger getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Override
    public Stranger getUser()
    {
        return getContact();
    }
}
