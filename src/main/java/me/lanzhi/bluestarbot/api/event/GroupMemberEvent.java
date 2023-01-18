package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.GroupMember;

/**
 * 群成员相关事件,也是一种群事件,一种用户事件
 * @see GroupEvent 群事件
 * @see UserEvent 用户事件
 */
public interface GroupMemberEvent extends GroupEvent, UserEvent
{
    /**
     * @return 涉及到的群员
     */
    public default GroupMember getMember()
    {
        return getUser();
    }

    /**
     * @return 涉及到的群员
     */
    public GroupMember getUser();

    /**
     * @return 涉及到的机器人
     */
    @Override
    Bot getBot();


    /**
     * @return 涉及到的群
     */
    @Override
    public default Group getGroup()
    {
        return getUser().getGroup();
    }
}
