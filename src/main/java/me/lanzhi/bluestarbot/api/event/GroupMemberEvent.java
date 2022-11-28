package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.GroupMember;

public interface GroupMemberEvent extends GroupEvent, UserEvent
{
    public default GroupMember getMember()
    {
        return getUser();
    }

    public GroupMember getUser();

    @Override
    Bot getBot();

    @Override
    public default Group getGroup()
    {
        return getUser().getGroup();
    }
}
