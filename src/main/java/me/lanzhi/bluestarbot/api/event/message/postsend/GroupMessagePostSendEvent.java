package me.lanzhi.bluestarbot.api.event.message.postsend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.api.event.MessagePostSendEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 群消息发送事件
 */
public final class GroupMessagePostSendEvent extends BluestarBotEvent implements MessagePostSendEvent, GroupEvent
{
    @Internal
    public GroupMessagePostSendEvent(net.mamoe.mirai.event.events.GroupMessagePostSendEvent event)
    {
        super(event);
    }

    @Override
    public Group getGroup()
    {
        return getContact();
    }

    @Override
    public Group getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.GroupMessagePostSendEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupMessagePostSendEvent) super.getEvent();
    }
}
