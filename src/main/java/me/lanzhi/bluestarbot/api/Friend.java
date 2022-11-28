package me.lanzhi.bluestarbot.api;

import net.mamoe.mirai.utils.ExternalResource;

public final class Friend extends User implements SendAudioAble
{
    public Friend(net.mamoe.mirai.contact.Friend friend)
    {
        super(friend);
    }
    
    public net.mamoe.mirai.contact.Friend getFriend()
    {
        return (net.mamoe.mirai.contact.Friend) super.getUser();
    }

    @Override
    public Contact.Type getChatType()
    {
        return Contact.Type.FRIEND;
    }

    @Override
    public String getName()
    {
        return getFriend().getNick();
    }

    @Override
    public User.Type getUserType()
    {
        return User.Type.FRIEND;
    }


    public void delete()
    {
        getFriend().delete();
    }

    @Override
    public void sendAudio(ExternalResource resource)
    {
        this.getFriend().sendMessage(this.getFriend().uploadAudio(resource.toAutoCloseable()));
    }

    @Override
    public String toString()
    {
        return "Friend{bot:"+getBot().getId()+",id:"+getId()+"}";
    }
}
