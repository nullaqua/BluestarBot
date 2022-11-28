package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;

public final class MemberJoinRequestEvent extends BluestarBotEvent implements GroupEvent
{
    public MemberJoinRequestEvent(net.mamoe.mirai.event.events.MemberJoinRequestEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.MemberJoinRequestEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberJoinRequestEvent) super.getEvent();
    }

    @Override
    public Group getGroup()
    {
        return Mapping.map(getEvent().getGroup());
    }

    public long getUserId()
    {
        return getEvent().getFromId();
    }

    public String getUserName()
    {
        return getEvent().getFromNick();
    }

    public String getMessage()
    {
        return getEvent().getMessage();
    }

    public NormalGroupMember getInvitor()
    {
        return Mapping.map(getEvent().getInvitor());
    }

    public void accept()
    {
        getEvent().accept();
    }

    public void reject()
    {
        reject(false);
    }

    public void reject(boolean blackList)
    {
        reject(blackList,"");
    }

    public void reject(boolean blackList,String message)
    {
        getEvent().reject(blackList,message);
    }

    public void ignore()
    {
        ignore(false);
    }

    public void ignore(boolean blackList)
    {
        getEvent().ignore(blackList);
    }
}
