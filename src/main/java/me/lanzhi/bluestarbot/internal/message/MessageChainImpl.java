package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.message.Message;
import me.lanzhi.bluestarbot.api.message.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

@Internal
public class MessageChainImpl implements MessageChain, MessageImpl
{
    private final List<Message> list;
    private final net.mamoe.mirai.message.data.MessageChain message;

    public MessageChainImpl(List<Message> list)
    {
        this.list=list;
        MessageChainBuilder builder=new MessageChainBuilder();
        for (Message message1: list)
        {
            builder.add(((MessageImpl) message1).toMirai());
        }
        this.message=builder.build();
    }

    @Override
    public @NotNull Iterator<Message> iterator()
    {
        return list.iterator();
    }

    @Override
    public net.mamoe.mirai.message.data.Message toMirai()
    {
        return message;
    }

    @Override
    public String getDisplay(Contact contact)
    {
        StringBuilder builder=new StringBuilder();
        for (Message message1: list)
        {
            builder.append(message1.getDisplay(contact));
        }
        return builder.toString();
    }
}
