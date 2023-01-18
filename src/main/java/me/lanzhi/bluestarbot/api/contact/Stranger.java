package me.lanzhi.bluestarbot.api.contact;

import me.lanzhi.bluestarbot.api.Internal;

/**
 * 陌生人
 */
public final class Stranger extends User
{    @Internal
    public Stranger(net.mamoe.mirai.contact.Stranger stranger)
    {
        super(stranger);
    }
    @Internal
    public net.mamoe.mirai.contact.Stranger getStranger()
    {
        return (net.mamoe.mirai.contact.Stranger) super.getUser();
    }

    @Override
    public String getName()
    {
        return getStranger().getNick();
    }

    /**
     * 删除此人
     */
    public void delete()
    {
        getStranger().delete();
    }

    @Override
    public Contact.Type getType()
    {
        return Contact.Type.Stranger;
    }
}
