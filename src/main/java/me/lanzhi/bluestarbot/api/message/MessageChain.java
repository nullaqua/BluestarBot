package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.internal.message.MessageChainImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * 一个消息链,由许多单个的消息构成
 */
public interface MessageChain extends Message, Iterable<Message>
{
    /**
     * @return 一个消息链构造器
     */
    static Builder builder()
    {
        return new Builder();
    }

    @NotNull
    @Override
    Iterator<Message> iterator();

    class Builder
    {
        private final ArrayList<Message> list=new ArrayList<>();

        private Builder()
        {
        }

        public Builder append(Message message)
        {
            if (message instanceof MessageChain)
            {
                for (Message message1: (MessageChain) message)
                {
                    this.append(message1);
                }
            }
            list.add(message);
            return this;
        }

        public MessageChain build()
        {
            return new MessageChainImpl(Collections.unmodifiableList(list));
        }
    }
}
