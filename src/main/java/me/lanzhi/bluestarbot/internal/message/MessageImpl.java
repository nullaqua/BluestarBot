package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.message.Message;

public interface MessageImpl extends Message
{
    public default String contentToString()
    {
        return toMirai().contentToString();
    }

    public net.mamoe.mirai.message.data.Message toMirai();
}
