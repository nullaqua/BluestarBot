package me.lanzhi.bluestarbot.api.contact.group;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Contact;
import net.mamoe.mirai.contact.AnonymousMember;

/**
 * 匿名群员
 */
public final class AnonymousGroupMember extends GroupMember
{
    @Internal
    public AnonymousGroupMember(AnonymousMember member)
    {
        super(member);
    }

    @Internal
    public AnonymousMember getMember()
    {
        return (AnonymousMember) super.getMember();
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
