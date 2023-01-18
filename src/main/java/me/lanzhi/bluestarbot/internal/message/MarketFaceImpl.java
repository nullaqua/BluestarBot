package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.message.Message;

@Internal
public class MarketFaceImpl implements MessageImpl, Message
{
    private final net.mamoe.mirai.message.data.MarketFace face;

    public MarketFaceImpl(net.mamoe.mirai.message.data.MarketFace face)
    {
        this.face=face;
    }

    @Override
    public net.mamoe.mirai.message.data.MarketFace toMirai()
    {
        return face;
    }
}
