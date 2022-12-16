package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.internal.Mapping;
import me.lanzhi.bluestarbot.internal.message.ForwardMessageImpl;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * 一个合并转发
 * 可遍历其中包含的每条消息
 */
public interface ForwardMessage extends Message, Iterable<ForwardMessage.Node>
{
    /**
     * 创建一个合并转发的builder
     *
     * @param contact 聊天频道
     * @return builder
     */
    static Builder builder(Contact contact)
    {
        assert contact!=null:"Contact is null! BluestarBot ForwardMessage.class";
        return new Builder(contact);
    }

    @NotNull
    @Override
    Iterator<Node> iterator();

    /**
     * @return 其中包含的消息条数
     */
    int getSize();

    /**
     * @see Display 显示方案
     */
    default String title()
    {
        return getDisplay().title(this);
    }

    /**
     * @return 显示方案
     * @see Display 显示方案
     */
    Display getDisplay();

    /**
     * @see Display 显示方案
     */
    default String brief()
    {
        return getDisplay().brief(this);
    }

    /**
     * @see Display 显示方案
     */
    default List<String> preview()
    {
        return getDisplay().preview(this);
    }

    /**
     * @see Display 显示方案
     */
    default String summary()
    {
        return getDisplay().summary(this);
    }

    /**
     * 合并转发消息的渲染<br>
     * |-------------------------|<br>
     * |  title                  |<br>
     * |  preview                |<br>
     * |-------------------------|<br>
     * |  summary                |<br>
     * |-------------------------|
     * <p>
     * 默认显示方案:<br>
     * |-------------------------|<br>
     * | 群聊的聊天记录             |<br>
     * |  消息 1                  |<br>
     * |  消息 2                  |<br>
     * |  消息 3                  |<br>
     * |-------------------------|<br>
     * | 查看3条转发消息           |<br>
     * |-------------------------|
     */
    public static interface Display
    {
        Display DEFAULT=new Display()
        {
        };

        /**
         * 修改后卡片标题会变为 "转发的聊天记录", 而此函数的返回值会显示在 preview 前
         */
        default String title(ForwardMessage message)
        {
            return "群聊的聊天记录";
        }

        /**
         * 显示在消息列表中的预览.
         */
        default String brief(ForwardMessage message)
        {
            return "[聊天记录]";
        }

        /**
         * 显示在卡片preview中的内容,只会显示列表前4个
         */
        default List<String> preview(ForwardMessage message)
        {
            List<String> list=new ArrayList<>();
            message.forEach(node->list.add(node.name+": "+node.messages.contentToString()));
            return list;
        }

        /**
         * 显示在卡片底部
         */
        default String summary(ForwardMessage message)
        {
            return "查看"+message.getSize()+"条转发消息";
        }
    }

    /**
     * 转发消息中的一条
     */
    class Node
    {
        /**
         * 发送者id
         */
        private final long id;
        /**
         * 发送者名称
         */
        private final String name;
        /**
         * 消息
         */
        private final MessageChain messages;
        /**
         * 发送时间
         */
        private final Date date;

        public Node(User user,Message message,Date date)
        {
            this(user.getId(),user.getName(),message,date);
        }

        public Node(long id,String name,Message message,Date date)
        {
            this.id=id;
            this.name=name;
            this.messages=Mapping.asMessageChain(message);
            this.date=date;
        }

        public long id()
        {
            return id;
        }

        public String name()
        {
            return name;
        }

        public MessageChain messages()
        {
            return messages;
        }

        public Date date()
        {
            return date;
        }
    }

    /**
     * 合并转发构建器
     */
    class Builder
    {
        private final ArrayList<Node> messages=new ArrayList<>();
        private Contact contact;
        private Display display=Display.DEFAULT;

        private Builder(Contact contact)
        {
            this.contact=contact;
        }

        public Builder display(Display display)
        {
            if (display!=null)
            {
                this.display=display;
            }
            return this;
        }

        public Display display()
        {
            return display;
        }

        public Builder append(long senderId,String senderName,Message message)
        {
            messages.add(new Node(senderId,senderName,message,new Date()));
            return this;
        }

        public Builder append(MessageReceivedEvent event)
        {
            return append(event.getSender(),event.getMessage());
        }

        public Builder append(User user,MessageChain message)
        {
            messages.add(new Node(user,message,new Date()));
            return this;
        }

        public Builder add(int i,MessageReceivedEvent event)
        {
            return add(i,event.getSender(),event.getMessage());
        }

        public Builder add(int i,User user,MessageChain message)
        {
            messages.add(i,new Node(user,message,new Date()));
            return this;
        }

        public Builder append(int i,long senderId,String senderName,Message message)
        {
            messages.add(i,new Node(senderId,senderName,message,new Date()));
            return this;
        }

        public ForwardMessage build()
        {
            return new ForwardMessageImpl(Collections.unmodifiableList(messages),contact,display);
        }

        public Contact contact()
        {
            return contact;
        }

        public Builder contact(Contact contact)
        {
            if (contact==null)
            {
                return this;
            }
            this.contact=contact;
            return this;
        }
    }
}
