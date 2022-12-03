package me.lanzhi.bluestarbot.api;

import me.lanzhi.bluestarbot.Mapping;
import net.mamoe.mirai.contact.UserOrBot;

import java.util.Objects;

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

    public final String getRemark()
    {
        return Mapping.toUser(getUser()).getRemark();
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
        return null;
    }
}
