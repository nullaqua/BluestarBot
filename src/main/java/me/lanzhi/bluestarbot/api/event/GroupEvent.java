package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.group.Group;

/**
 * 群聊相关事件
 */
public interface GroupEvent
{
    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * @return 涉及到的群聊
     */
    public Group getGroup();
}
