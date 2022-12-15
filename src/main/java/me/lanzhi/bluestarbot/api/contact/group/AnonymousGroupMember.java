package me.lanzhi.bluestarbot.api.contact.group;

import me.lanzhi.bluestarbot.api.contact.Contact;
import net.mamoe.mirai.contact.AnonymousMember;

/**
 * 匿名群员
 */
public final class AnonymousGroupMember extends GroupMember
{
    public AnonymousGroupMember(AnonymousMember member)
    {
        super(member);
    }

    @Override
    public String toString()
    {
        return "AnonymousGroupMember{bot:"+getBot().getId()+",group:"+getGroup().getId()+"}";
    }

    @Override
    public Contact.Type getType()
    {
        return Contact.Type.AnonymousMember;
    }
}
