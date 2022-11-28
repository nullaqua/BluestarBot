package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Stranger;
import me.lanzhi.bluestarbot.api.event.StrangerEvent;
import net.mamoe.mirai.message.data.MessageSource;

public final class StrangerMessagePostSendEvent extends UserMessagePostSendEvent implements StrangerEvent
{
    public StrangerMessagePostSendEvent(net.mamoe.mirai.event.events.StrangerMessagePostSendEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.StrangerMessagePostSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.StrangerMessagePostSendEvent) super.getEvent();
    }

    @Override
    public void recall()
    {
        MessageSource.recall(getEvent().getMessage());
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
