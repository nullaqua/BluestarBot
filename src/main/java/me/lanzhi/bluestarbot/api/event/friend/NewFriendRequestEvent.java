package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;

public final class NewFriendRequestEvent extends BluestarBotEvent
{
    public NewFriendRequestEvent(net.mamoe.mirai.event.events.NewFriendRequestEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.NewFriendRequestEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.NewFriendRequestEvent) super.getEvent();
    }

    public String getUserName()
    {
        return getEvent().getFromNick();
    }

    public long getUserId()
    {
        return getEvent().getFromId();
    }

    public Group getFromGroup()
    {
        return Mapping.map(getEvent().getFromGroup());
    }

    public boolean isFromGroup()
    {
        return getEvent().getFromGroup()!=null;
    }

    public void reject()
    {
        reject(false);
    }

    public void reject(boolean blackList)
    {
        getEvent().reject(blackList);
    }

    public void accept()
    {
        getEvent().accept();
    }

    public String getMessage()
    {
        return getEvent().getMessage();
    }
}
