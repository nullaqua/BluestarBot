package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.message.Dice;

public final class DiceImpl implements MessageImpl, Dice
{
    private final net.mamoe.mirai.message.data.Dice dice;

    public DiceImpl(int id)
    {
        assert id<=6&&id>=1:"id:1~6";
        this.dice=new net.mamoe.mirai.message.data.Dice(id);
    }

    public static DiceImpl random()
    {
        return new DiceImpl(net.mamoe.mirai.message.data.Dice.random().getId());
    }

    public int getId()
    {
        return dice.getId();
    }

    @Override
    public net.mamoe.mirai.message.data.Message toMirai()
    {
        return dice;
    }
}
