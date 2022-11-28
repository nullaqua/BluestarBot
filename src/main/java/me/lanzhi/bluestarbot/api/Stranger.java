package me.lanzhi.bluestarbot.api;

public final class Stranger extends User
{
    public Stranger(net.mamoe.mirai.contact.Stranger stranger)
    {
        super(stranger);
    }
    
    public net.mamoe.mirai.contact.Stranger getStranger()
    {
        return (net.mamoe.mirai.contact.Stranger) super.getUser();
    }

    @Override
    public String getName()
    {
        return getStranger().getNick();
    }

    @Override
    public User.Type getUserType()
    {
        return User.Type.STRANGER;
    }

    public void delete()
    {
        getStranger().delete();
    }

    @Override
    public Contact.Type getChatType()
    {
        return Contact.Type.STRANGER;
    }
}
