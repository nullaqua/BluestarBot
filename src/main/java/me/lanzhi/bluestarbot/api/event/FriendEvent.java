package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.Friend;

/**
 * 好友相关事件
 */
public interface FriendEvent extends UserEvent
{
    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * @return 所涉及好友
     */
    @Override
    public Friend getUser();

    /**
     * @return 所涉及好友
     */
    public default Friend getFriend()
    {
        return getUser();
    }
}
