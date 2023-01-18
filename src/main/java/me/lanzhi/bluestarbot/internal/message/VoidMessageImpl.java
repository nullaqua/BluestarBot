package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.message.VoidMessage;
import net.mamoe.mirai.message.data.LightApp;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.ServiceMessage;

@Internal
public class VoidMessageImpl implements VoidMessage, MessageImpl
{
    private final Message message;

    public VoidMessageImpl(Message message)
    {
        this.message=message;
    }

    @Override
    public Type getType()
    {
        if (message instanceof LightApp)
        {
            return Type.LightAPP;
        }
        if (message instanceof ServiceMessage)
        {
            return Type.ServiceMessage;
        }
        return Type.Unknown;
    }

    @Override
    public Message toMirai()
    {
        return message;
    }
}
