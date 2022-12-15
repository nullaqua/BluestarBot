package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.internal.message.DiceImpl;

/**
 * 一个筛子
 */
public interface Dice extends Message
{
    /**
     * @return 一个随机点数的筛子
     */
    static Dice random()
    {
        return DiceImpl.random();
    }

    /**
     * 创建一个特定点数的筛子
     *
     * @param id 点数
     * @return 筛子
     */
    static Dice create(int id)
    {
        return new DiceImpl(id);
    }

    /**
     * @return 筛子的ID
     */
    int getId();
}
