package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.api.Bluestar;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.internal.Mapping;
import me.lanzhi.bluestarbot.internal.message.MessageChainImpl;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.MessageSourceBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.Date;
import java.util.Objects;

/**
 * 回复
 */
public class Reply
{
    /**
     * 被回复的消息的发送者
     */
    private final long sender;
    /**
     * 被回复的消息的发送时间
     */
    private final Date time;
    /**
     * 被回复的消息内容
     */
    private final MessageChain oMessage;
    /**
     * 被回复的消息的id(不清楚是什么id)
     */
    private final int id;
    /**
     * 被回复的消息的id(不清楚是什么id)
     */
    private final long internalId;
    private final MessageSourceBuilder messageSource;

    @Internal
    public Reply(QuoteReply reply)
    {
        this(reply.getSource());
    }

    @Internal
    public Reply(MessageSource source)
    {
        this.sender=source.getFromId();
        this.time=new Date(source.getTime()*1000L);
        this.oMessage=Mapping.map(source.getOriginalMessage());
        this.id=source.getIds()[0];
        this.internalId=source.getInternalIds()[0];
        this.messageSource=new MessageSourceBuilder().allFrom(source);
    }

    /**
     * @param sender   被回复的消息的发送者
     * @param oMessage 被回复的消息内容
     */
    public Reply(User sender,MessageChain oMessage)
    {
        this(sender.getId(),oMessage);
    }

    /**
     * @param sender   被回复的消息的发送者
     * @param oMessage 被回复的消息内容
     */
    public Reply(long sender,MessageChain oMessage)
    {
        this(sender,new Date(),oMessage);
    }

    /**
     * @param sender   被回复的消息的发送者
     * @param time     被回复的消息的发送时间
     * @param oMessage 被回复的消息内容
     */
    public Reply(long sender,Date time,MessageChain oMessage)
    {
        this(sender,time,oMessage,Bluestar.randomInt(),Bluestar.randomInt());
    }

    /**
     * @param sender     被回复的消息的发送者
     * @param time       被回复的消息的发送时间
     * @param oMessage   被回复的消息内容
     * @param id         被回复的消息的id(不清楚是什么id)
     * @param internalId 被回复的消息的id(不清楚是什么id)
     */
    @Internal
    public Reply(long sender,Date time,MessageChain oMessage,int id,int internalId)
    {
        this.sender=sender;
        this.time=time;
        this.oMessage=oMessage;
        this.id=id;
        this.internalId=internalId;
        messageSource=new MessageSourceBuilder();
        messageSource.id(id)
                     .internalId(internalId)
                     .sender(sender)
                     .time((int) (time.getTime()/1000))
                     .messages(((MessageChainImpl) oMessage).toMirai());
    }

    /**
     * 从收到的一条消息构造
     *
     * @param event 用于构造的消息所属的事件
     */
    public Reply(MessageReceivedEvent event)
    {
        this(Objects.requireNonNull(event.getEvent().getMessage().get(MessageSource.Key)));
    }

    /**
     * @param sender   被回复的消息的发送者
     * @param time     被回复的消息的发送时间
     * @param oMessage 被回复的消息内容
     */
    public Reply(User sender,Date time,MessageChain oMessage)
    {
        this(sender.getId(),time,oMessage);
    }

    /**
     * @param sender     被回复的消息的发送者
     * @param time       被回复的消息的发送时间
     * @param oMessage   被回复的消息内容
     * @param id         被回复的消息的id(不清楚是什么id)
     * @param internalId 被回复的消息的id(不清楚是什么id)
     */
    @Internal
    public Reply(User sender,Date time,MessageChain oMessage,int id,int internalId)
    {
        this(sender.getId(),time,oMessage,id,internalId);
    }

    public long getSender()
    {
        return sender;
    }

    public Date getTime()
    {
        return time;
    }

    public MessageChain getoMessage()
    {
        return oMessage;
    }

    public int getId()
    {
        return id;
    }

    public long getInternalId()
    {
        return internalId;
    }

    @Internal
    public QuoteReply toMirai(Bot bot,Contact contact)
    {
        return new QuoteReply(messageSource.target(contact.getToContact())
                                           .build(bot.getId(),contact.getType().asMiraiMessageSourceKind()));
    }
}
