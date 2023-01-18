package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.message.At;
import net.mamoe.mirai.message.data.AtAll;

@Internal
public final class AtImpl implements MessageImpl, At
{
    public final static MessageImpl AT_ALL=()->AtAll.INSTANCE;
    private final long id;

    public AtImpl(long id)
    {
        this.id=id;
    }

    public AtImpl(net.mamoe.mirai.message.data.At at)
    {
        this.id=at.getTarget();
    }

    public long getId()
    {
        return id;
    }

    @Override
    public net.mamoe.mirai.message.data.At toMirai()
    {
        return new net.mamoe.mirai.message.data.At(id);
    }

    @Override
    public String getDisplay(Contact contact)
    {
        User user=contact.getUser(id);
        return "@"+(user==null?"未知人员":user.getName());
    }
}
