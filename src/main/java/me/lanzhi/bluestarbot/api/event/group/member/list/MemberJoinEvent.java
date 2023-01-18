package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 用户加入群聊事件
 */
public final class MemberJoinEvent extends BluestarBotEvent implements GroupMemberEvent
{
    @Internal
    public MemberJoinEvent(net.mamoe.mirai.event.events.MemberJoinEvent event)
    {
        super(event);
    }

    @Override
    public NormalGroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.MemberJoinEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberJoinEvent) super.getEvent();
    }
}
