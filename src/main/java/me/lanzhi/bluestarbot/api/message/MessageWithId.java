package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.message.data.MessageSource;

import java.util.Objects;

public final class MessageWithId
{
    public final MessageId id;
    public final MessageChain message;

    public MessageWithId(MessageId id,MessageChain message)
    {
        this.id=id;
        this.message=message;
    }

    @Internal
    public static MessageWithId map(net.mamoe.mirai.message.data.MessageChain message)
    {
        var id=message.get(MessageSource.Key);
        var message0=Mapping.map(message);
        Objects.requireNonNull(id);
        return new MessageWithId(new MessageId(id.getIds()[0],id.getInternalIds()[0]),message0);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof MessageWithId&&((MessageWithId) obj).id.equals(id);
    }
}