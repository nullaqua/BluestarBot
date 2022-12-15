package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;

/**
 * 群设置发生变化事件
 */
public interface GroupSettingChangeEvent extends GroupEvent
{
    /**
     * @return 是否在群内广播
     */
    public boolean getShouldBroadcast();

    /**
     * @return 所属机器人
     */
    public Bot getBot();

    /**
     * @return 旧设置
     */
    public Object getOld();

    /**
     * @return 新设置
     */
    public Object getNew();
}
