package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;

public final class BotInvitedJoinGroupRequestEvent extends BluestarBotEvent
{
    public BotInvitedJoinGroupRequestEvent(net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent event)
    {
        super(event);
    }

    public void accept()
    {
        getEvent().accept();
    }

    @Override
    public net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent) super.getEvent();
    }

    public void ignore()
    {
        getEvent().ignore();
    }

    public String getGroupName()
    {
        return getEvent().getGroupName();
    }

    public long getGroupId()
    {
        return getEvent().getGroupId();
    }

    public Friend getInvitor()
    {
        return Mapping.map(getEvent().getInvitor());
    }
}
