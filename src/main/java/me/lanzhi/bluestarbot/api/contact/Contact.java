package me.lanzhi.bluestarbot.api.contact;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.group.AnonymousGroupMember;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.GroupMember;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.message.Message;
import me.lanzhi.bluestarbot.api.message.Reply;
import me.lanzhi.bluestarbot.internal.Mapping;
import me.lanzhi.bluestarbot.internal.message.MessageImpl;
import net.mamoe.mirai.contact.ContactOrBot;
import net.mamoe.mirai.message.data.MessageSourceKind;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;

/**
 * 一个聊天频道
 *
 * <p>注: 每个聊天频道,包括用户等,都属于一个机器人。所以两个机器人的同一位QQ好友需要单独获取.通过{@link #getBot()}获取机器人</p>
 *
 * @see User 用户
 * @see Friend 好友
 * @see Stranger 陌生人
 * @see OtherClient 其他客户端
 * @see Group 群聊
 * @see GroupMember 群员
 */
public abstract class Contact
{
    @Internal
    private final ContactOrBot contact;
    @Internal
    private final net.mamoe.mirai.contact.Contact toContact;

    public Contact(ContactOrBot contact)
    {
        assert contact!=null;
        this.contact=contact;
        this.toContact=Mapping.toContact(contact);
    }

    /**
     * 在此聊天频道中向一个用户发送戳一戳
     *
     * @param user 发送的对象
     * @return 成功为true
     */
    public final boolean nudge(User user)
    {
        return nudge(user.getId());
    }

    /**
     * 在此聊天频道中向一个用户发送戳一戳
     *
     * @param id 发送的对象的qqid
     * @return 成功为true
     */
    public abstract boolean nudge(long id);

    @Override
    public final boolean equals(Object obj)
    {
        if (obj instanceof Contact)
        {
            return contact.equals(((Contact) obj).contact);
        }
        return contact.equals(obj);
    }

    @Internal
    public ContactOrBot getContact()
    {
        return contact;
    }

    @Internal
    public net.mamoe.mirai.contact.Contact getToContact()
    {
        return toContact;
    }

    /**
     * 聊天频道的id,群聊则为群聊qq号,好友则为好友qq号
     *
     * @return 聊天频道的id
     */
    public final long getId()
    {
        return contact.getId();
    }

    /**
     * 发送纯文本的消息
     *
     * @param message 消息内容
     */
    public final void sendMessage(String message)
    {
        toContact.sendMessage(message);
    }

    /**
     * @param message mirai代码
     * @deprecated 以mirai码发送消息,不建议使用.建议使用:{@link #sendMessage(Message)}和{@link #sendMessage(Message,Reply)}
     */
    @Deprecated(since="1.4.0",forRemoval=true)
    public final void sendMessageCode(String message)
    {
        sendMessage(me.lanzhi.bluestarbot.api.message.Message.getFromMiraiCode(message));
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @see me.lanzhi.bluestarbot.api.message.Message 消息
     */
    public final void sendMessage(me.lanzhi.bluestarbot.api.message.Message message)
    {
        toContact.sendMessage(((MessageImpl) message).toMirai());
    }

    /**
     * 发送一个消息,并回复另一消息
     *
     * @param messages 消息
     * @param reply    回复信息
     * @see me.lanzhi.bluestarbot.api.message.Message 消息
     * @see Reply 回复
     */
    public final void sendMessage(me.lanzhi.bluestarbot.api.message.Message messages,Reply reply)
    {
        if (reply==null)
        {
            sendMessage(messages);
            return;
        }
        toContact.sendMessage(reply.toMirai(getBot(),this).plus(((MessageImpl) messages).toMirai()));
    }

    /**
     * 此群聊所属的机器人
     *
     * @return 机器人
     */
    public final Bot getBot()
    {
        return Mapping.map(contact.getBot());
    }

    /**
     * 获取聊天中的一个用户,在不存在时返回null
     * 在群聊中既获取一群成员,在两人的聊天中既只能获取到双方
     *
     * @param id 用户qqid
     * @return 用户
     */
    public abstract User getUser(long id);

    /**
     * 获取此聊天频道的名称,群聊则为群聊名称,好友等则为其昵称
     *
     * @return 名称
     */
    public abstract String getName();

    /**
     * 聊天类型
     *
     * @return 类型
     * @see Type 类型
     */
    public abstract Type getType();

    /**
     * 头像的下载链接,好友头像或群头像
     *
     * @return 链接
     */
    public final String getAvatarUrl()
    {
        return contact.getAvatarUrl();
    }

    /**
     * 发送音乐分享
     *
     * @param type       分享种类
     * @param title      分享标题
     * @param summary    内容
     * @param jumpUrl    点击后跳转链接
     * @param pictureUrl 图片的链接
     * @param musicUrl   音乐链接
     */
    public void sendMusicShare(MusicType type,String title,String summary,String jumpUrl,String pictureUrl,
                               String musicUrl)
    {
        toContact.sendMessage(new MusicShare(type.kind,title,summary,jumpUrl,pictureUrl,musicUrl));
    }

    /**
     * 音乐类型
     */
    public enum MusicType
    {
        QQMusic(MusicKind.QQMusic),
        MiguMusic(MusicKind.MiguMusic),
        KugouMusic(MusicKind.KugouMusic),
        KuwoMusic(MusicKind.KuwoMusic),
        NeteaseCLoudMusic(MusicKind.NeteaseCloudMusic);
        private final MusicKind kind;

        MusicType(MusicKind kind)
        {
            this.kind=kind;
        }
    }

    /**
     * 聊天类型
     */
    public enum Type
    {
        /**
         * 对应{@link Bot}
         */
        Bot(MessageSourceKind.FRIEND),
        /**
         * 对应{@link Group}
         */
        Group(MessageSourceKind.GROUP),
        /**
         * 对应{@link Friend}
         */
        Friend(MessageSourceKind.FRIEND),
        /**
         * 对应{@link Stranger}
         */
        Stranger(MessageSourceKind.STRANGER),
        /**
         * 对应{@link NormalGroupMember}
         */
        NormalMember(MessageSourceKind.TEMP),
        /**
         * 对应{@link AnonymousGroupMember}
         */
        AnonymousMember(MessageSourceKind.TEMP),
        /**
         * 对应{@link OtherClient}
         */
        OtherClient(null),
        /**
         * 未知聊天类型,理论上不会出现
         */
        Unknown(null);
        @Internal
        private final MessageSourceKind kind;

        @Internal
        Type(MessageSourceKind kind)
        {
            this.kind=kind;
        }

        @Internal
        public MessageSourceKind asMiraiMessageSourceKind()
        {
            return kind;
        }
    }
}
