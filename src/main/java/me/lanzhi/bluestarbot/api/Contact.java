package me.lanzhi.bluestarbot.api;

import me.lanzhi.api.Bluestar;
import me.lanzhi.bluestarbot.Mapping;
import net.mamoe.mirai.contact.ContactOrBot;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class Contact
{
    private final ContactOrBot contact;
    private final net.mamoe.mirai.contact.Contact toContact;

    public Contact(ContactOrBot contact)
    {
        assert contact!=null;
        this.contact=contact;
        this.toContact=Mapping.toContact(contact);
    }

    public final boolean nudge(User user)
    {
        return nudge(user.getId());
    }

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

    public ContactOrBot getContact()
    {
        return contact;
    }

    public net.mamoe.mirai.contact.Contact getToContact()
    {
        return toContact;
    }

    public final void sendMessage(String message)
    {
        toContact.sendMessage(message);
    }

    public final void sendMessageCode(String message)
    {
        toContact.sendMessage(MiraiCode.deserializeMiraiCode(message));
    }

    public final String uploadImage(File file)
    {
        return uploadImage0(ExternalResource.create(file));
    }

    protected final String uploadImage0(ExternalResource resource)
    {
        return toContact.uploadImage(resource.toAutoCloseable()).getImageId();
    }

    public final void sendFlashImage(File file)
    {
        this.sendFlashImage(this.uploadImage(file));
    }

    public final void sendFlashImage(InputStream stream) throws IOException
    {
        this.sendFlashImage(this.uploadImage(stream));
    }

    public final void sendFlashImage(String image)
    {
        toContact.sendMessage(FlashImage.from(image));
    }

    public final String uploadImage(InputStream inputStream) throws IOException
    {
        return uploadImage0(ExternalResource.create(inputStream));
    }

    public abstract String getName();

    public final long getId()
    {
        return contact.getId();
    }

    public final Bot getBot()
    {
        return Mapping.map(contact.getBot());
    }

    public void reply(User user,String originalMessage,String replyMessage)
    {
        reply(user,System.currentTimeMillis(),originalMessage,replyMessage);
    }

    public void reply(User user,long time,String originalMessage,String replyMessage)
    {
        reply(user,time,originalMessage,replyMessage,Bluestar.randomInt(),Bluestar.randomInt());
    }

    public void reply(User user,long time,String originalMessage,String replyMessage,int id0,int id1)
    {
        reply(user.getId(),time,originalMessage,replyMessage,id0,id1);
    }

    public void reply(long sender,long time,String originalMessage,String replyMessage,int id0,int id1)
    {
        MessageSourceBuilder sourceBuilder=new MessageSourceBuilder();
        sourceBuilder.id(id0).internalId(id1).time((int) (time/1000L)).setFromId(sender);
        sourceBuilder.messages(MiraiCode.deserializeMiraiCode(originalMessage)).target(getContact());
        QuoteReply reply=new QuoteReply(sourceBuilder.build(getBot().getId(),getType().asMiraiMessageSourceKind()));

        MessageChainBuilder builder=new MessageChainBuilder().append(reply);
        MiraiCode.deserializeMiraiCode(replyMessage,toContact).forEach(builder::append);
        toContact.sendMessage(builder.asMessageChain());
    }

    public abstract Type getType();

    public void reply(long sender,String originalMessage,String replyMessage)
    {
        reply(sender,System.currentTimeMillis(),originalMessage,replyMessage);
    }

    public void reply(long sender,long time,String originalMessage,String replyMessage)
    {
        reply(sender,time,originalMessage,replyMessage,Bluestar.randomInt(),Bluestar.randomInt());
    }

    public final String getAvatarUrl()
    {
        return contact.getAvatarUrl();
    }

    /**
     * 发送音乐分享<br>
     *
     * @param type       可选种类：QQMusic | MiguMusic | KugouMusic | KuwoMusic | NeteaseCLoudMusic
     * @param title      标题
     * @param summary    内容
     * @param jumpUrl    跳转链接
     * @param pictureUrl 图片链接
     * @param musicUrl   音乐链接
     */
    public void sendMusicShare(MusicType type,String title,String summary,String jumpUrl,String pictureUrl,String musicUrl)
    {
        toContact.sendMessage(new MusicShare(type.kind,title,summary,jumpUrl,pictureUrl,musicUrl));
    }

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

    public enum Type
    {
        Bot(MessageSourceKind.FRIEND),
        Group(MessageSourceKind.GROUP),
        Friend(MessageSourceKind.FRIEND),
        Stranger(MessageSourceKind.STRANGER),
        NormalMember(MessageSourceKind.TEMP),
        AnonymousMember(MessageSourceKind.TEMP),
        OtherClient(null),
        Unknown(null);
        private final MessageSourceKind kind;

        Type(MessageSourceKind kind)
        {
            this.kind=kind;
        }

        public MessageSourceKind asMiraiMessageSourceKind()
        {
            return kind;
        }
    }
}
