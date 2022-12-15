package me.lanzhi.bluestarbot.api.event.message.received;

import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.message.data.MessageSource;

import java.util.function.BooleanSupplier;

/**
 * 接受到群消息
 */
public final class GroupMessageEvent extends BluestarBotEvent implements MessageReceivedEvent, GroupMemberEvent
{
    public GroupMessageEvent(net.mamoe.mirai.event.events.GroupMessageEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.GroupMessageEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.GroupMessageEvent) super.getEvent();
    }

    @Override
    public Group getContact()
    {
        return Mapping.map(getEvent().getGroup());
    }

    @Override
    public GroupMember getSender()
    {
        return Mapping.map(getEvent().getSender());
    }

    @Override
    public Group getGroup()
    {
        return getContact();
    }

    @Override
    public GroupMember getUser()
    {
        return getSender();
    }

    public boolean recall()
    {
        try
        {
            MessageSource.recall(getEvent().getMessage());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public BooleanSupplier recall(int seconds)
    {
        try
        {
            return MessageSource.recallIn(getEvent().getMessage(),seconds)::awaitIsSuccess;
        }
        catch (Exception e)
        {
            return ()->false;
        }
    }
}
