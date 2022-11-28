package me.lanzhi.bluestarbot.api;

import me.lanzhi.bluestarbot.Mapping;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.UserOrBot;

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

    public abstract Type getUserType();

    public final String getRemark()
    {
        return Mapping.toUser(getUser()).getRemark();
    }

    public final void nudge()
    {
        if (getUser() instanceof net.mamoe.mirai.contact.User)
        {
            getUser().nudge().sendTo((net.mamoe.mirai.contact.User) getUser());
        }
        else if (getUser() instanceof Bot)
        {
            getUser().nudge().sendTo(((Bot) getUser()).getAsFriend());
        }
    }

    @Override
    public String getName()
    {
        return null;
    }

    public enum Type
    {
        FRIEND,STRANGER,MEMBER
    }
}
