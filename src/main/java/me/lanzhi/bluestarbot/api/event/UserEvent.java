package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.User;

/**
 * 用户相关事件
 */
public interface UserEvent
{
    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * @return 所属用户
     */
    public User getUser();
}
