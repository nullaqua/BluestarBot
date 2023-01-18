package me.lanzhi.bluestarbot.api.event.group.member;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupMemberEvent;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;

/**
 * 群员头衔修改事件
 *
 * @see NormalGroupMember#setTitle(String) 设置头衔
 */
public final class MemberTitleChangeEvent extends BluestarBotEvent implements GroupMemberEvent
{
    @Internal
    public MemberTitleChangeEvent(MemberSpecialTitleChangeEvent event)
    {
        super(event);
    }

    /**
     * @return 操作人
     */
    public NormalGroupMember getOperator()
    {
        return Mapping.map(getEvent().getOperator());
    }

    @Internal
    public MemberSpecialTitleChangeEvent getEvent()
    {
        return (MemberSpecialTitleChangeEvent) super.getEvent();
    }

    public String getNew()
    {
        return getEvent().getNew();
    }

    public String getOld()
    {
        return getEvent().getOrigin();
    }

    @Override
    public NormalGroupMember getUser()
    {
        return Mapping.map(getEvent().getMember());
    }
}
