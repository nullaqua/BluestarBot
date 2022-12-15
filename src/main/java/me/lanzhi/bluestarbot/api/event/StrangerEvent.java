package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.Stranger;

/**
 * 陌生人相关事件
 */
public interface StrangerEvent extends UserEvent
{
    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * @return 所属的陌生人
     */
    @Override
    public Stranger getUser();

    /**
     * @return 所属的陌生人
     */
    public default Stranger getStranger()
    {
        return getUser();
    }
}
