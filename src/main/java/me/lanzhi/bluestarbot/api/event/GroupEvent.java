package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.group.Group;

/**
 * 群聊相关事件
 * <p>群事件不一定发生在群内,例如一群员通过群临时聊天向机器人发送消息,也被认为是群事件</p>
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
