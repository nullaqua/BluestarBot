package me.lanzhi.bluestarbot.api.event.message;

import me.lanzhi.bluestarbot.internal.Mapping;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;

/**
 * 戳一戳
 */
public final class NudgeEvent extends BluestarBotEvent
{
    public NudgeEvent(net.mamoe.mirai.event.events.NudgeEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.NudgeEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.NudgeEvent) super.getEvent();
    }

    /**
     * @return 接受戳一戳的人
     */
    public User getRecipient()
    {
        return getTo();
    }

    /**
     * @return 接受戳一戳的人
     */
    public User getTo()
    {
        return Mapping.map(getEvent().getTarget());
    }

    /**
     * 向此戳一戳的发送者发送戳一戳
     */
    public void nudgeToSender()
    {
        getContact().nudge(getSender());
    }

    /**
     * @return 发送的聊天
     */
    public Contact getContact()
    {
        return Mapping.map(getEvent().getSubject());
    }

    /**
     * @return 发送戳一戳的人
     */
    public User getSender()
    {
        return getFrom();
    }

    /**
     * @return 发送戳一戳的人
     */
    public User getFrom()
    {
        return Mapping.map(getEvent().getFrom());
    }

    /**
     * 向此戳一戳的接受者发送戳一戳
     */
    public void nudgeToRecipient()
    {
        getContact().nudge(getTo());
    }
}
