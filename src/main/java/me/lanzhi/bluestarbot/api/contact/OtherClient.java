package me.lanzhi.bluestarbot.api.contact;

/**
 * 其他客户端
 */
public final class OtherClient extends Contact
{
    public OtherClient(net.mamoe.mirai.contact.OtherClient otherClient)
    {
        super(otherClient);
    }

    public net.mamoe.mirai.contact.OtherClient getOtherClient()
    {
        return (net.mamoe.mirai.contact.OtherClient) super.getContact();
    }

    @Override
    public boolean nudge(long id)
    {
        return false;
    }

    @Override
    public User getUser(long id)
    {
        return null;
    }

    @Override
    public String getName()
    {
        return getOtherClient().getInfo().getDeviceName();
    }


    @Override
    public Type getType()
    {
        return Type.OtherClient;
    }

    @Override
    public String toString()
    {
        return "OtherClient{bot:"+getBot().getId()+"}";
    }
}
