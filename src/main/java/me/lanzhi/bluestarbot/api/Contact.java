package me.lanzhi.bluestarbot.api;

import me.lanzhi.bluestarbot.Mapping;
import net.mamoe.mirai.contact.ContactOrBot;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;
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

    public final void sendMessage(String message)
    {
        toContact.sendMessage(message);
    }

    public final void sendMessageCode(String message)
    {
        toContact.sendMessage(message);
    }

    public final String uploadImage(File file)
    {
        return uploadImage(ExternalResource.create(file));
    }

    public final String uploadImage(InputStream inputStream) throws IOException
    {
        return uploadImage(ExternalResource.create(inputStream));
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

    protected final String uploadImage(ExternalResource resource)
    {
        return toContact.uploadImage(resource.toAutoCloseable()).getImageId();
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

    public abstract Type getChatType();

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
        GROUP,FRIEND,STRANGER,MEMBER,OTHER_CLIENT
    }
}
