package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.OtherClient;

/**
 * 其他客户端相关事件
 */
public interface OtherClientEvent
{
    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * @return 所属的其他客户端
     */
    public OtherClient getOtherClient();
}
