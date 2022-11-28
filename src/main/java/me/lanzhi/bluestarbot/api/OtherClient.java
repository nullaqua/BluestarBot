package me.lanzhi.bluestarbot.api;

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
    public String getName()
    {
        return getOtherClient().getInfo().getDeviceName();
    }

    @Override
    public Type getChatType()
    {
        return Type.OTHER_CLIENT;
    }
    @Override
    public String toString()
    {
        return "OtherClient{bot:"+getBot().getId()+"}";
    }
}
