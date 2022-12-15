package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.internal.message.AtImpl;

/**
 * 提及一个群成员
 */
public interface At extends Message
{
    /**
     * 提及全体成员
     */
    Message AT_ALL=AtImpl.AT_ALL;

    /**
     * 创建一个提及
     *
     * @param id 提及对象
     * @return 一个提及
     */
    static At create(long id)
    {
        return new AtImpl(id);
    }

    /**
     * @return 提及的对象
     */
    long getId();
}
