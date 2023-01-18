package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 用户离开群聊事件
 */
public final class MemberLeaveEvent extends BluestarBotEvent implements GroupMemberEvent
{
    @Internal
    public MemberLeaveEvent(net.mamoe.mirai.event.events.MemberLeaveEvent event)
    {
        super(event);
    }

    @Override
    public GroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.MemberLeaveEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberLeaveEvent) super.getEvent();
    }
}
