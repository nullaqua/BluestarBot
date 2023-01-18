package me.lanzhi.bluestarbot.api.contact;

import me.lanzhi.bluestarbot.api.Internal;
import net.mamoe.mirai.contact.AudioSupported;

/**
 * 好友
 *
 * @see SendAudioAble 可发送语音
 */
public final class Friend extends User implements SendAudioAble
{
    @Internal
    public Friend(net.mamoe.mirai.contact.Friend friend)
    {
        super(friend);
    }

    @Override
    public Contact.Type getType()
    {
        return Type.Friend;
    }

    @Override
    public String getName()
    {
        return getFriend().getNick();
    }

    /**
     * 删除此好友
     */
    public void delete()
    {
        getFriend().delete();
    }
    @Internal
    public net.mamoe.mirai.contact.Friend getFriend()
    {
        return (net.mamoe.mirai.contact.Friend) super.getUser();
    }

    public AudioSupported getAudioSupported()
    {
        return getFriend();
    }

    @Override
    public String toString()
    {
        return "Friend{bot:"+getBot().getId()+",id:"+getId()+"}";
    }
}
