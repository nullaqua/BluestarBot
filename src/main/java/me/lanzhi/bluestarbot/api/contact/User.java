package me.lanzhi.bluestarbot.api.contact;

import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.contact.UserOrBot;

import java.util.Objects;

/**
 * 一个用户,用户也是一个聊天频道
 *
 * @see Contact 聊天频道
 */
public abstract class User extends Contact
{
    protected User(UserOrBot contact)
    {
        super(contact);
    }

    public UserOrBot getUser()
    {
        return (UserOrBot) super.getContact();
    }

    /**
     * 备注
     *
     * @return 备注
     */
    public final String getRemark()
    {
        return Mapping.toUser(getUser()).getRemark();
    }

    @Override
    public final User getUser(long id)
    {
        if (id==getId())
        {
            return this;
        }
        if (id==getBot().getId())
        {
            return getBot();
        }
        return null;
    }

    @Override
    public boolean nudge(long id)
    {
        if (id!=getUser().getId())
        {
            return false;
        }
        getUser().nudge().sendTo(Objects.requireNonNull(Mapping.toContact(getUser())));
        return true;
    }

    @Override
    public String getName()
    {
        return getUser().getNick();
    }
}
